package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.RefreshTokenEntity;
import net.javaguides.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(RefreshTokenEntity user);
    Optional<RefreshTokenEntity> findByUser(User user);
}

