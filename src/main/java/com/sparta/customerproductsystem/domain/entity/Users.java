package com.sparta.customerproductsystem.domain.entity;

import com.sparta.customerproductsystem.domain.role.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
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

    // orphanRemoval 고아 삭제
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>(); // NPE 방지

    void addReview(Review review) {
        if (review == null) return;
        this.reviews.add(review);
    }

    public Users(String email, String password, String name, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}


