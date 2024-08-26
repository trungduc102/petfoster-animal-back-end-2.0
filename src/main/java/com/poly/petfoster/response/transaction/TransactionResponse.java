package com.poly.petfoster.response.transaction;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionResponse {
    private int id;
    private Date when;
    private Double amount;
    private String description;
    private int cusum_balance;
    private String tid;
    private String subAccId;
    private String bank_sub_acc_id;
    private String virtualAccount;
    private String virtualAccountName;
    private String corresponsiveName;
    private String corresponsiveAccount;
    private String corresponsiveBankId;
    private String corresponsiveBankName;
}
