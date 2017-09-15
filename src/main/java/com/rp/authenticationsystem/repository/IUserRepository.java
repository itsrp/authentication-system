package com.rp.authenticationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rp.authenticationsystem.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

	
}
