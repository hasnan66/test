package com.my.test.controller;

import com.my.test.dto.Response.AccountResponseDto;
import com.my.test.dto.request.AccountRequestDto;
import com.my.test.security.UserDetailsImpl;
import com.my.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/statements", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountResponseDto getAccountStatements(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody(required = false) AccountRequestDto accountRequestDto) {
        AccountResponseDto responseDto = new AccountResponseDto();
        // we can get for role as well.
        if (user.getUsername().equalsIgnoreCase("testAdmin")) {
            responseDto = accountService.getAccountStatements(accountRequestDto);

        } else {
            if (accountRequestDto.getAccountId() != null || accountRequestDto.getToAmount() != null || accountRequestDto.getFromAmount() != null ||
                    accountRequestDto.getToDate() != null || accountRequestDto.getFromDate() != null) {
                responseDto.setErrorCode(401);
                responseDto.setErrorMessage("unauthorized!");
            } else {
                responseDto = accountService.getAccountStatements(accountRequestDto);
            }
        }

        return responseDto;
    }
}
