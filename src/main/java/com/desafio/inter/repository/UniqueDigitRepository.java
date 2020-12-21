package com.desafio.inter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.inter.model.UniqueDigit;

@Repository
public interface UniqueDigitRepository extends JpaRepository<UniqueDigit, Long> {
	
	List<UniqueDigit> findAllByUserId(Long userId);
}
