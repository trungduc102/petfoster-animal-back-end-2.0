package com.poly.petfoster.request.transaction;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionRequest {
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date when;
    private Double amount;
    private String description;
    private int cusum_balance;
    private String tid;
    private String subAccId;
    private String bank_sub_acc_id;
    private String bankName;
    private String bankAbbreviation;
    private String virtualAccount;
    private String virtualAccountName;
    private String corresponsiveName;
    private String corresponsiveAccount;
    private String corresponsiveBankId;
    private String corresponsiveBankName;

}
