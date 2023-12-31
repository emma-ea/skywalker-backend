package io.github.emmaea.skywalker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LOCATION")
@NoArgsConstructor
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "LATITUDE")
    @NotNull
    private double latitude;
    @Column(name = "LONGITUDE")
    @NotNull
    private double longitude;
    @Column(name = "NAME")
    @NotEmpty
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Shop shop;
    @OneToOne(mappedBy = "location", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Vendor vendor;
    // @OneToOne(mappedBy = "homeLocation", cascade = CascadeType.PERSIST)
    // @JsonBackReference
    // private User user;
}
