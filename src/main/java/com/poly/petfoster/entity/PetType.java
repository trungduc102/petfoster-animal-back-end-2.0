package com.poly.petfoster.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PetType {
    
    @Id
    @Column(name = "type_id")
    private String id;
    @Nationalized
    @Column(name = "type_name")
    private String name;

    @OneToMany(mappedBy = "petType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PetBreed> breeds = new ArrayList<>();
    
}
