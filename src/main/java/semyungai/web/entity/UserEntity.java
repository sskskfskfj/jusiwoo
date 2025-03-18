package semyungai.web.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public UserEntity(String username, String name, String password, String email){
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = Role.STUDENT;
    }

    public void update(String name, String email){
        this.name = name;
        this.email = email;
    }
}
