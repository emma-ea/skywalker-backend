package io.github.emmaea.skywalker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BUSINESS")
@NoArgsConstructor
@Getter
@Setter
public class Business {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "EMPLOYEES_SIZE")
    @NotNull
    private long employeesSize;
    @Column(name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private BusinessType type;
    @OneToOne(mappedBy = "businessType", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Shop shop;
}
