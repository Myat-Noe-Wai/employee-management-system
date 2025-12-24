package net.javaguides.springboot.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int userId;
	
    @Column(name="user_name", length = 255)
    private String username;
    
    @Column(name="email", length = 255)
    private String email;
    
    @Column(name="password", length = 255)
    private String password;
    
    @Column(name="role", length = 255)
    private String role;
}

