package com.demo.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.springjwt.models.Account;
import com.demo.springjwt.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

  	Boolean existsByUsername(String username);
  
//  @Query(value = "SELECT * FROM users u WHERE "
//  		+ "EXISTS (SELECT * FROM user_account, account, users WHERE "
//  		+ "user_account.user_id = users.id AND user_account.account_id = account.id "
//  		+ "AND account.account_number = :accountNumber)", nativeQuery = true)
//  Optional<User> findUserByAccountNumber(
//		  @Param("accountNumber") Long accountNumber);
  
//  	@Query("SELECT u FROM User u JOIN u.accounts")
//	Optional<User> findUserByAccount(@Param("account") Account account);
}
