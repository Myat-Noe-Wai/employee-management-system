package net.javaguides.springboot.Impl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	Optional<User> findOneByEmailAndPassword(String email, String password);
	User findByEmail(String email);
}
