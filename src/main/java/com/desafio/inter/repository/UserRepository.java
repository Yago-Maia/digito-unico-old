package com.desafio.inter.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.inter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
