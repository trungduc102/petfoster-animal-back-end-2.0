package com.poly.petfoster.request.adopts;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelAdoptRequest {
    
    @NotBlank(message = "Cancel Reason can't be blank!!!")
    private String cancelReason;

}
