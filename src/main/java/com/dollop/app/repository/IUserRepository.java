package com.dollop.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.entity.User;

public interface IUserRepository extends JpaRepository<User, String> {

	boolean existsByEmail(String email);

	boolean existsByUserName(String userName);

	Optional<User> findByEmail(String username);
	
	boolean existsByEmailAndIdNot(String email, String id);

	List<User> findByIdNot(String id);
}
