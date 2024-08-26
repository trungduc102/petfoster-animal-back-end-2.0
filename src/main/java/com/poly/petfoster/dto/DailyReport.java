package com.poly.petfoster.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DailyReport {

    @Id
    private Integer orderstotal;

    private Double revenuetotal;

}
