package net.javaguides.springboot.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
//@Table(name = "\"user\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
    @Column(name="user_id", length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
	
    @Column(name="user_name", length = 255)
    private String username;
    
    @Column(name="email", length = 255)
    private String email;
    
    @Column(name="password", length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}

