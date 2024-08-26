package com.poly.petfoster.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders order;

    // @ManyToOne
    // @JoinColumn(name = "product_id")
    // @JsonIgnore
    // private Product product;

    private Integer quantity;

    private Double price;

    private Double total;

    // @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<ProductRepo> repos;

    @OneToOne
    @JoinColumn(name = "product_repo_id")
    private ProductRepo productRepo;

}
