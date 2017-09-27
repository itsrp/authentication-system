package com.rp.authenticationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rp.authenticationsystem.model.Token;
import com.rp.authenticationsystem.model.User;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Long>{

	Integer countByUser(User user);

	Token findByUserAndIsExpired(User user, boolean b);

}
