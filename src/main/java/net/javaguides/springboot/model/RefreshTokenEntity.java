package net.javaguides.springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "refresh_token")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public RefreshTokenEntity() {
    }

    public RefreshTokenEntity(String refreshToken, User savedUserEntity, LocalDateTime localDateTime) {
        this.token = refreshToken;
        this.user = savedUserEntity;
        this.expiryDate = localDateTime;
    }
}

