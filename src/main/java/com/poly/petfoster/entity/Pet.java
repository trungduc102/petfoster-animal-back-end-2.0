package com.poly.petfoster.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Entity
@Builder
public class Pet {

    @Id
    private String petId;

    private String petName;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    @JsonIgnore
    private PetBreed petBreed;

    private Boolean sex;

    @Nationalized
    private String petColor;
    
    @Nationalized
    private String age;

    private Boolean isSpay;

    @CreationTimestamp
    private Date createAt;

    private Date fosterAt;
    @Nationalized
    private String descriptions;
    @Nationalized
    private String adoptStatus;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PetImgs> imgs;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Favorite> favorites;

}
