package com.poly.petfoster.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;

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
public class Product {

    @Id
    @Column(name = "product_id")
    @Nationalized
    private String id;

    @Column(name = "product_name")
    @Nationalized
    private String name;

    @Column(name = "product_desc")
    @Nationalized
    private String desc;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private ProductType productType;

    private Boolean isActive;

    // brand of product
    // private String brand;

    @CreationTimestamp
    @Column(name = "create_at")
    private Date createAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductRepo> productsRepo;

    // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Imgs> imgs;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RecentView> recentViews;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brand brand;

}
