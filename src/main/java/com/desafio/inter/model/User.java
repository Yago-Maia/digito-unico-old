package com.desafio.inter.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificador do usuário", required = true)
	private Long id;

	@Column(name = "name", length=2048)
	@ApiModelProperty(notes = "Nome do usuário", required = true)
	@NotNull
	private String name;

	@Column(name = "email", length=2048)
	@ApiModelProperty(notes = "Email do usuário", required = true)
	@NotNull
	private String email;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	@ApiModelProperty(notes = "Lista de Dígitos Únicos do usuário", required = false)
	private List<UniqueDigit> uniqueDigitList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<UniqueDigit> getUniqueDigitList() {
		return uniqueDigitList;
	}

	public void setUniqueDigitList(List<UniqueDigit> uniqueDigitList) {
		this.uniqueDigitList = uniqueDigitList;
	}

}
