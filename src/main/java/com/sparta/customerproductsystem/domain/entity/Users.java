package com.sparta.customerproductsystem.domain.entity;

import com.sparta.customerproductsystem.domain.role.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Size(min = 8, message = "비밀번호는 최소 8자 이상, 영문,숫자,특수문자 조합을 권장합니다.")
    @Column(nullable = false)
    private String password;
    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    public Users(String email, String password, String name, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}


