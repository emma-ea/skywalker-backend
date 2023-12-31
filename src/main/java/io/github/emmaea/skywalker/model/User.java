package io.github.emmaea.skywalker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.emmaea.skywalker.model.request.SignUpRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "APP_USER")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "MIDDLENAME")
    private String middleName;
    @Column(name = "EMAIL")
    @Email
    private String email;
    @Column(name = "PASSWORD")
    @NotEmpty
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLE")
    private UserRole role;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Column(name = "PHONE_NUMBER")
    private String phone;
    @Column(name = "DATE_OF_BIRTH")
    @Temporal(value = TemporalType.DATE)
    private LocalDate dob;
    @Column(name = "LAST_LOGIN")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime lastLogin;
    @Column(name = "FIRST_SIGNUP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime firstSignUp;
//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
//    @JsonManagedReference
//    private Location homeLocation;


    public static User from(SignUpRequest request) {
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setMiddleName(request.getMiddleName());
        newUser.setLastName(request.getLastName());
        newUser.setDob(request.getDob());
        newUser.setEmail(request.getEmail());
        newUser.setCountryCode(request.getCountryCode());
        newUser.setPhone(request.getPhone());
        // newUser.setPassword();
        newUser.setRole(UserRole.USER);
        newUser.setLastLogin(null);
        newUser.setFirstSignUp(LocalDateTime.now());
        return newUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
