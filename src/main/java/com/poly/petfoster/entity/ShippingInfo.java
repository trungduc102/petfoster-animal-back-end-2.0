package com.poly.petfoster.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
public class ShippingInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // @JsonIgnore
    // private User user;
    @Nationalized
    private String fullName;
    @Nationalized
    private String address;
    @Nationalized
    private String province;
    @Nationalized
    private String district;
    @Nationalized
    private String ward;

    private String phone;

    private Integer shipFee;

    @OneToMany(mappedBy = "shippingInfo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Orders> orders;

    @ManyToOne
    @JoinColumn(name = "delivery_company_id")
    @JsonIgnore
    private DeliveryCompany deliveryCompany;
    
}
