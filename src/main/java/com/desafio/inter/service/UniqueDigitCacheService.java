package com.desafio.inter.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.desafio.inter.model.UniqueDigit;

@Service
public class UniqueDigitCacheService {
	
	public static List<UniqueDigit> uniqueDigitQueue = new ArrayList<UniqueDigit>();
	
	public void insertUniqueDigit(UniqueDigit uniqueDigit) {
		boolean aux = isFull();
		
		if (!aux) {
			uniqueDigitQueue.add(uniqueDigit);
		} else {
			deleteUniqueDigit();
			uniqueDigitQueue.add(uniqueDigit);
		}
	}
	
	public UniqueDigit getFromQueue(String n, int k) {

		if (!uniqueDigitQueue.isEmpty()) {
			Iterator<UniqueDigit> iterator = uniqueDigitQueue.iterator();
			while (iterator.hasNext()) {
				UniqueDigit uniqueDigit = iterator.next();
				if (uniqueDigit.getK() == k && uniqueDigit.getN().equals(n)) {
					return uniqueDigit;
				}
			}
		}

		return null;
	}
	
	public void deleteUniqueDigit() {
		uniqueDigitQueue.remove(0);
	}
	
	public boolean isFull() {
		if (uniqueDigitQueue.size() == 10) {
			return true;
		}
		else {
			return false;
		}
	}
}