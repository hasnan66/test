package com.my.test.dto.Response;

import com.my.test.dto.Statement;
import lombok.Data;

import java.util.List;

@Data
public class AccountResponseDto {

    private int errorCode;
    private String errorMessage;
    private List<Statement> data;
}
