package io.github.emmaea.skywalker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "VENDOR")
@NoArgsConstructor
@Getter
@Setter
public class Vendor {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "FIRSTNAME")
    @NotEmpty
    private String firstName;
    @Column(name = "MIDDLENAME")
    private String middleName;
    @Column(name = "LASTNAME")
    @NotEmpty
    private String lastName;
    @Column(name = "EMAIL")
    @Email
    private String email;
    @Column(name = "PHONE_COUNTRY_CODE")
    @NotNull
    private String countryCode;
    @Column(name = "PHONE_NUMBER")
    @NotNull
    private String phone;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SHOP_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Set<Shop> ownedShops;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
    @JsonManagedReference
    private Location location;
}
