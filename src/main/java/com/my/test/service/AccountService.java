package com.my.test.service;

import com.my.test.dto.Response.AccountResponseDto;
import com.my.test.dto.request.AccountRequestDto;

public interface AccountService {

    public AccountResponseDto getAccountStatements(AccountRequestDto accountRequestDto);
}
