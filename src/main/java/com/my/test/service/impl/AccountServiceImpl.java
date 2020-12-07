package com.my.test.service.impl;

import com.my.test.dto.Account;
import com.my.test.dto.Response.AccountResponseDto;
import com.my.test.dto.Statement;
import com.my.test.dto.request.AccountRequestDto;
import com.my.test.repository.AccountRepo;
import com.my.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public AccountResponseDto getAccountStatements(AccountRequestDto accountRequestDto) {
        AccountResponseDto responseDto = new AccountResponseDto();
        try {
            if (accountRequestDto.getAccountId() != null) {
                if (accountRequestDto.getFromDate() != null && accountRequestDto.getToDate() != null) {
                    if (validateDate(accountRequestDto.getFromDate()) && validateDate(accountRequestDto.getToDate())) {
                        // get account details for input account Id
                        Account account = accountRepo.getAccountDetails(accountRequestDto.getAccountId());
                        if (account.getId() != null) {
                            // get account Id statements
                            List<Statement> statementList = accountRepo.getAccountStatements(account.getId());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
                            if (accountRequestDto.getFromAmount() != null && accountRequestDto.getToAmount() != null) {
                                // filter statements for given date and amount ranges
                                statementList = statementList.stream()
                                        .filter(obj -> LocalDate.parse(obj.getDateField().replace(".", "-"), formatter).
                                                isAfter(LocalDate.parse(accountRequestDto.getFromDate(), formatter)) &&
                                                LocalDate.parse(obj.getDateField().replace(".", "-"), formatter).
                                                        isBefore(LocalDate.parse(accountRequestDto.getToDate(), formatter)) &&
                                                (Double.parseDouble(obj.getAmount()) >= accountRequestDto.getFromAmount() &&
                                                        (Double.parseDouble(obj.getAmount()) <= accountRequestDto.getToAmount())))
                                        .collect(Collectors.toList());
                            } else {
                                // filter statements for given date ranges
                                statementList = statementList.stream().filter(obj -> LocalDate.parse(obj.getDateField().replace(".", "-"), formatter).
                                        isAfter(LocalDate.parse(accountRequestDto.getFromDate(), formatter)) &&
                                        LocalDate.parse(obj.getDateField().replace(".", "-"), formatter).
                                                isBefore(LocalDate.parse(accountRequestDto.getToDate(), formatter)))
                                        .collect(Collectors.toList());
                            }
                            responseDto.setData(statementList);
                        } else {
                            responseDto.setErrorCode(400);
                            responseDto.setErrorMessage(" given Id is invalid");
                        }
                    } else {
                        responseDto.setErrorCode(400);
                        responseDto.setErrorMessage(" given Dates are invalid");
                    }
                } else {
                    Account account = accountRepo.getAccountDetails(accountRequestDto.getAccountId());
                    if (account.getId() != null) {
                        // filter statements for given amount ranges
                        List<Statement> statementList = accountRepo.getAccountStatements(account.getId());
                        if (accountRequestDto.getFromAmount() != null && accountRequestDto.getToAmount() != null) {
                            statementList = statementList.stream().filter(obj -> Double.parseDouble(obj.getAmount()) >= accountRequestDto.getFromAmount() &&
                                    (Double.parseDouble(obj.getAmount()) <= accountRequestDto.getToAmount()))
                                    .collect(Collectors.toList());
                        }
                        responseDto.setData(statementList);
                    }
                }
            } else {
                List<Statement> statementList = accountRepo.getAccountStatements();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
                // filter statements for last 3 months
                statementList = statementList.stream().filter(obj -> LocalDate.parse(obj.getDateField().replace(".", "-"), formatter).
                        isAfter(LocalDate.now().minusMonths(3)) &&
                        LocalDate.parse(obj.getDateField().replace(".", "-"), formatter).
                                isBefore(LocalDate.now()))
                        .collect(Collectors.toList());
                responseDto.setData(statementList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setErrorCode(500);
            responseDto.setErrorMessage(e.getMessage());
        }
        if (responseDto.getData().size() > 0) {
            responseDto.getData().stream().forEach(obj -> obj.setAccountNumber(getMd5(obj.getAccountNumber())));
        }
        return responseDto;
    }

    // validate input date
    private boolean validateDate(String date) {
        boolean valid = false;
        if (date != null && !date.equalsIgnoreCase("")) {

            try {
                // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
                LocalDate.parse(date,
                        DateTimeFormatter.ofPattern("d-M-uuuu")
                                .withResolverStyle(ResolverStyle.STRICT)
                );
                valid = true;

            } catch (DateTimeParseException e) {
                e.printStackTrace();
                valid = false;
            }
        }
        return valid;
    }

    public String getMd5(String input) {
        try {

            // getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }// For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
