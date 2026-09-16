package com.function.controller;

import com.function.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /*@GetMapping("/get_loan_details")
    public ResponseEntity<String> getLoanDetails(
            @RequestParam String accountNumber,
            @RequestParam String branchCode) {

        return ResponseEntity.ok(
                customerService.getLoanDetails(
                        accountNumber,
                        branchCode));
    }*/

    @GetMapping("/get_loan_details")
    public ResponseEntity<String> getLoanDetails(
            @RequestParam String accountNumber,
            @RequestParam String branchCode) {

        long start = System.currentTimeMillis();

        String result = customerService.getLoanDetails(
                accountNumber, branchCode);

        System.out.println("API Total Time is : "
                + (System.currentTimeMillis() - start) + " ms");

        return ResponseEntity.ok(result);
    }
}
