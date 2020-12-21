package com.desafio.inter.model;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;

import com.desafio.inter.errorHandler.BlobConversionException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class UniqueDigit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificação do Dígito Único", required = true)
	private Long id;
	
	@ApiModelProperty(notes = "Valor N", required = true)
	@Transient
	private String n;
	
	@JsonIgnore
	@Column(name="n") 
    @Lob 
    @NotNull
    private Blob nBlob;
	
	@ApiModelProperty(notes = "Valor K", required = true)
	@NotNull
	private Integer k;
	
	@ApiModelProperty(notes = "Resultado", required = true)
	@NotNull
	private int result;

	@Column(name = "userId")
	@ApiModelProperty(notes = "Identificador do usuário", required = false)
	@NotNull
	private Long userId;

	public String getN() {
		if(this.nBlob != null) {
			try {
				return new String(this.nBlob.getBytes(1l, (int) this.nBlob.length()));
			} catch (Exception e) {
				throw new BlobConversionException();
			}
		} else {
			return n;
		}
	}

	public void setN(String n) {
		this.n = n;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Blob getnBlob() {
		return nBlob;
	}

	public void setnBlob(Blob nBlob) {
		this.nBlob = nBlob;
	}
	
	public void setBlobFromN(String n) {
		try {
			this.nBlob = new SerialBlob(n.getBytes());
		} catch (Exception e) {
			throw new BlobConversionException();
		}
	}
}
