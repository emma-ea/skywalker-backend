package io.github.emmaea.skywalker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    @NotEmpty
    private String name;
    @Column(name = "DETAIL")
    private String detail;
    @Column(name = "PRICE")
    @NotNull
    private double price;
    @Column(name = "CURRENCY")
    @NotNull
    private String currency;
    @Column(name = "RATING")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private double rating;
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "PRODUCT_IMAGES", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @Column(name = "IMAGES")
    private Set<String> images = new HashSet<>();
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Shop shop;
}
