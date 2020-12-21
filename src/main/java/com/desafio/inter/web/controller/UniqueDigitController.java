package com.desafio.inter.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.desafio.inter.model.UniqueDigit;
import com.desafio.inter.service.UniqueDigitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/digitoUnico")
public class UniqueDigitController {

	@Autowired
	UniqueDigitService uniqueDigitService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Calcula um Dígito Único", notes = "Calcula um Dígito Único", response = UniqueDigit.class)
	@ApiResponses({ 
		@ApiResponse(code = 201, message = "Dígito único calculado com sucesso."),
		@ApiResponse(code = 401, message = "Não autorizado"),
	    @ApiResponse(code = 403, message = "Recurso proibido"),
	    @ApiResponse(code = 404, message = "Recurso não encontrado") })
	@PostMapping("/calcula")
	public int computeUniqueDigit(HttpServletRequest request, @RequestBody UniqueDigit uniqueDigit) {
		try {
			Long userId = request.getHeader("userId") == null || 
					request.getHeader("userId").isEmpty() ? null : Long.valueOf(request.getHeader("userId"));
			
			return uniqueDigitService.computeAndSaveUniqueDigit(uniqueDigit, userId);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@ApiOperation(value = "Lista Dígitos Únicos do usuário", notes = "Lista Dígitos Únicos do usuário", response = UniqueDigit.class, responseContainer = "List")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Dígitos Únicos Listados com sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"),
		    @ApiResponse(code = 403, message = "Recurso proibido"),
		    @ApiResponse(code = 404, message = "Recurso não encontrado") })
	@GetMapping("/listar")
	public List<UniqueDigit> listUniqueDigit(@RequestHeader("userId") Long userId) {
		try {
			return uniqueDigitService.getAllByUser(userId);
		} catch (Exception ex) {
			throw ex;
		}
	}
}
