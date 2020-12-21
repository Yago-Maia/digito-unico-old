package com.desafio.inter.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.desafio.inter.errorHandler.DataBaseOperationException;
import com.desafio.inter.errorHandler.FieldValidationException;
import com.desafio.inter.model.UniqueDigit;
import com.desafio.inter.repository.UniqueDigitRepository;

@Service
public class UniqueDigitService {

	@Autowired
	UniqueDigitRepository uniqueDigitRepository;
	
	@Autowired
	OperationUniqueDigitService operationUniqueDigitService;

	@Autowired
	UniqueDigitCacheService uniqueDigitCacheService;
	
	@Autowired
	UserService userService;

	public int computeAndSaveUniqueDigit(UniqueDigit uniqueDigitRequest, Long userId) {
		
		this.checkFields(uniqueDigitRequest);
		userService.checkUserId(userId);
		
		UniqueDigit uniqueDigit = uniqueDigitCacheService.getFromQueue(uniqueDigitRequest.getN(), uniqueDigitRequest.getK());
		if(uniqueDigit == null) {
			int result = operationUniqueDigitService.computeUniqueDigit(uniqueDigitRequest.getN(), uniqueDigitRequest.getK());

			uniqueDigit = new UniqueDigit();
			uniqueDigit.setBlobFromN(uniqueDigitRequest.getN());
			uniqueDigit.setK(uniqueDigitRequest.getK());
			uniqueDigit.setResult(result);
			uniqueDigitCacheService.insertUniqueDigit(uniqueDigit);
		}
		
		if(userId != null) {
			uniqueDigit.setUserId(userId);
			try {
				uniqueDigitRepository.save(uniqueDigit);
			} catch (Exception e) {
				throw new DataBaseOperationException("An error occurred while saving unique digit.");
			}
		}
		
		return uniqueDigit.getResult();
	}

	public List<UniqueDigit> getAllByUser(Long userId) {
		userService.checkUserId(userId);
		try {
			return uniqueDigitRepository.findAllByUserId(userId);
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred while getting uniques digits.");
		}
	}
	
	private void checkFields (UniqueDigit uniqueDigit) {
		
		if(uniqueDigit.getK() == null || uniqueDigit.getN() == null || uniqueDigit.getN().length() == 0) {
			throw new FieldValidationException("Fields K and N are required.");
		} else if (!uniqueDigit.getN().matches("\\d+")) {
			throw new FieldValidationException("N should contain only numbers.");
		} else if (!(uniqueDigit.getK() >= 1 && uniqueDigit.getK() <= 100000)) {
			throw new FieldValidationException("K should be greater ou equals 1 and lower or equal than 10^5.");
		} else if (uniqueDigit.getN().length() > Math.pow(10, 1000000) || uniqueDigit.getN().equals("0")) {
			throw new FieldValidationException("N should be greater ou equals 1 and lower or equal than 10^1000000.");
		}
	}
}
