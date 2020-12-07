package com.my.test.repository;

import com.my.test.dto.Account;
import com.my.test.dto.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Statement> getAccountStatements(Long accountId) {
        String query = "Select ID, datefield, amount,   (Select account_number from account ac where ac.ID = statement.account_id) as account_number from statement where account_id =" + accountId + "";
        List<Statement> accountStatements = jdbcTemplate.query(query, new RowMapper<Statement>() {
            @Override
            public Statement mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Statement(resultSet.getLong("ID"), resultSet.getString("account_number"), resultSet.getString("datefield"), resultSet.getString("amount"));
            }
        });
        return accountStatements;
    }

    public List<Statement> getAccountStatements() {
        String query = "Select ID, datefield, amount,   (Select account_number from account ac where ac.ID = statement.account_id) as account_number from statement";
        List<Statement> accountStatements = jdbcTemplate.query(query, new RowMapper<Statement>() {
            @Override
            public Statement mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Statement(resultSet.getLong("ID"), resultSet.getString("account_number"), resultSet.getString("datefield"), resultSet.getString("amount"));
            }
        });
        return accountStatements;
    }

    public Account getAccountDetails(Long accountId) {
        String query = "Select * from account where ID =" + accountId + "";
        Account account = jdbcTemplate.queryForObject(query, new Object[]{}, (rs, rowNum) ->
                new Account(
                        rs.getLong("ID"),
                        rs.getString("account_type"),
                        rs.getString("account_number")
                ));

        return account;
    }

}
