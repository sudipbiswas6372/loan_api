package com.function.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Map;

@Repository
public class CustomerRepository {


    @Autowired
    private DataSource dataSource;
    public String getLoanDetails(String accountNumber, String branchCode) {

        long start = System.currentTimeMillis();

        try (Connection con = dataSource.getConnection();
             CallableStatement cs =
                     con.prepareCall("{call CLPKS_LOAN_UTILS_CUSTOM.FN_GET_LOAN_DETAILS(?,?,?)}")) {

            cs.setString(1, accountNumber);
            cs.setString(2, branchCode);
            cs.registerOutParameter(3, Types.VARCHAR);

            cs.execute();

            String result = cs.getString(3);

            System.out.println("Execution Time : " +
                    (System.currentTimeMillis() - start) + " ms");

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    /*public String getLoanDetails(String accountNumber, String branchCode) {

        String sql =
                "begin " +
                        " CLPKS_LOAN_UTILS_CUSTOM.FN_GET_LOAN_DETAILS(?,?,?);" +
                        " end;";

        try (
                Connection con = dataSource.getConnection();
                CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setString(1, accountNumber);
            cs.setString(2, branchCode);
            cs.registerOutParameter(3, Types.VARCHAR);

            long start = System.currentTimeMillis();

            cs.execute();

            long end = System.currentTimeMillis();

            System.out.println("Execution Time : " + (end - start) + " ms");

            return cs.getString(3);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

    /*@Autowired
    private JdbcTemplate jdbcTemplate;

    public String getLoanDetails(String accountNumber,
                                 String branchCode) {

        try {

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("CLPKS_LOAN_UTILS_CUSTOM") // Package Name
                    .withFunctionName("FN_GET_LAON_AMOUNT_DETAILS");

            String output = jdbcCall.executeFunction(
                    String.class,
                    new MapSqlParameterSource()
                            .addValue("P_ACCOUNT_NUMBER", accountNumber)
                            .addValue("P_BRANCH_CODE", branchCode)
            );

            return output;

        } catch (Exception e) {

            String error = e.getMessage();

            if (error != null && error.contains("ORA-01403")) {
                return "INVALID_ACCOUNT_OR_BRANCH";
            }

            throw new RuntimeException("Error fetching loan details", e);
        }*/
    }
}
