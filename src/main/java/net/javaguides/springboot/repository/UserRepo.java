package net.javaguides.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
	Optional<User> findOneByEmailAndPassword(String email, String password);
	Optional<User> findByEmail(String email);
}
