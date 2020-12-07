package com.my.test.dto;

import lombok.Data;

@Data
public class Statement {

    private Long id;
    private String accountNumber;
    private String dateField;
    private String amount;

    public Statement(Long id, String accountNumber, String dateField, String amount) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.dateField = dateField;
        this.amount = amount;
    }
}
