package com.my.test.dto.request;

import lombok.Data;

@Data
public class AccountRequestDto {

    private Long accountId;
    private String fromDate;
    private String toDate;
    private Double fromAmount;
    private Double toAmount;

}
