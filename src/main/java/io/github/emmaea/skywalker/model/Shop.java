package io.github.emmaea.skywalker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SHOP")
@NoArgsConstructor
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    @NotNull
    @NotEmpty
    private String name;
    @Column(name = "RATING")
    @DecimalMax(value = "5.0")
    @DecimalMin(value = "0.0")
    @NotNull
    private double rating;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BUSINESS_ID", referencedColumnName = "ID")
    @JsonManagedReference
    private Business businessType;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
    @JsonManagedReference
    private Set<Location> locations;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Vendor vendor;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    @JsonManagedReference
    private Set<Product> products;

}
