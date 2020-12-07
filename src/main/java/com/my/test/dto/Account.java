package com.my.test.dto;

import lombok.Data;

@Data
public class Account {

    private Long id;
    private String accountType;
    private String accountNumber;

    public Account(Long id, String accountType, String accountNumber) {
        this.id = id;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }
}
