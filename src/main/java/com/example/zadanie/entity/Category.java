package com.example.zadanie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GenericGenerator(name = "category-id", strategy = "com.example.zadanie.utils.CategoryIdGenerator")
    @GeneratedValue(generator = "category-id")
    private String id;
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> products;

}
