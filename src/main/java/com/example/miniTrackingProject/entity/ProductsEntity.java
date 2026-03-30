package com.example.miniTrackingProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsEntity implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

}
