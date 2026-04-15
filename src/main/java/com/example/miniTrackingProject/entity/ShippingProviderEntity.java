package com.example.miniTrackingProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shipping_providers")
@Getter
@Setter
public class ShippingProviderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String phone;

    private String email;

    private String website;

    private Boolean isActive = true;

    @OneToMany(mappedBy = "shippingProvider")
    private List<UserEntity> shipper;

    @OneToMany(mappedBy = "shippingProvider", cascade = CascadeType.ALL)
    private List<OrdersEntity> orders = new ArrayList<>();
}
