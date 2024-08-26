package com.poly.petfoster.request.adopts;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePickUpDateRequest {

    @NotNull(message = "Pick-up date can't be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date")
    private Date pickUpDate;
}
