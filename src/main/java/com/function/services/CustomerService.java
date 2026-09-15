package com.function.services;

import com.function.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public String getLoanDetails(String accountNumber,
                                 String branchCode) {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account Number is required");
        }

        if (branchCode == null || branchCode.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Branch Code is required");
        }

        String result =
                customerRepository.getLoanDetails(
                        accountNumber,
                        branchCode);

        if ("INVALID_ACCOUNT_OR_BRANCH".equals(result)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid Account Number or Branch Code");
        }

        if (result != null && result.startsWith("ERROR:")) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "System Error While Fetching Loan Details");
        }

        if (result == null || result.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Loan Details Not Found");
        }

        return result;
    }
}
