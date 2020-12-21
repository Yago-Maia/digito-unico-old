package com.desafio.inter.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.desafio.inter.model.User;
import com.desafio.inter.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/User")
public class UserController {

	@Autowired
	UserService userService;
	
	@ApiOperation(value = "Lista o usuário", notes = "Lista o usuário", response = User.class, responseContainer = "List")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Usuário Listado com sucesso"),
		@ApiResponse(code = 401, message = "Não autorizado"),
	    @ApiResponse(code = 403, message = "Recurso proibido"),
	    @ApiResponse(code = 404, message = "Recurso não encontrado") })
	@GetMapping(value = { "/{id}" })
	public User getUser(@PathVariable("id") Long id) {
		try {
			return userService.findById(id);
		} catch (Exception ex) {
			throw ex;
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Exclui um Usuário", notes = "Exclui um Usuário")
	@ApiResponses({ 
		@ApiResponse(code = 204, message = "Exclusão com sucesso de um usuário"),
		@ApiResponse(code = 401, message = "Não autorizado"),
	    @ApiResponse(code = 403, message = "Recurso proibido"),
	    @ApiResponse(code = 404, message = "Recurso não encontrado") })
	@DeleteMapping(value = { "/{id}" })
	public Boolean deleteUser(@PathVariable("id") Long id) {
		try {
			return userService.deleteUser(id);
		} catch (Exception ex) {
			throw ex;
		}
		
	}

	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Insere um Usuário com Dígito Único", notes = "Insere um Usuário com Dígito Único", response = User.class)
	@ApiResponses({ 
		@ApiResponse(code = 201, message = "Inclusão do Usuário com sucesso."),
		@ApiResponse(code = 401, message = "Não autorizado"),
	    @ApiResponse(code = 403, message = "Recurso proibido"),
	    @ApiResponse(code = 404, message = "Recurso não encontrado") })
	@PostMapping
	public User saveUSer(@RequestBody User user, @RequestHeader("publicKey") String publicKey) {
		try {
			return userService.saveUser(user, publicKey);
		} catch (Exception ex) {
			throw ex;
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Atualiza Usuário", notes = "Atualiza Usuário")
	@ApiResponses({ 
		@ApiResponse(code = 204, message = "Atualização com sucesso de um usuário"),
		@ApiResponse(code = 401, message = "Não autorizado"),
	    @ApiResponse(code = 403, message = "Recurso proibido"),
	    @ApiResponse(code = 404, message = "Recurso não encontrado") })
	@PutMapping
	public User editUser(@RequestBody User user, @RequestHeader("publicKey") String publicKey) {
		try {
			return userService.editUser(user, publicKey);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@ApiOperation(value = "Lista todos os Usuários", notes = "Lista todos os Usuários", response = User.class, responseContainer = "List" )
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Usuários listados com sucesso"),
		@ApiResponse(code = 401, message = "Não autorizado"),
	    @ApiResponse(code = 403, message = "Recurso proibido"),
	    @ApiResponse(code = 404, message = "Recurso não encontrado") })
	@GetMapping
	public List<User> findAllRatings() {
		try {
			return userService.findAll();
		} catch (Exception ex) {
			throw ex;
		}
	}

}
