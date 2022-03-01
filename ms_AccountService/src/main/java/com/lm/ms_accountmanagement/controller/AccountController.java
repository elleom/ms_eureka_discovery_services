package com.lm.ms_accountmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author el_le
 * @since 01/03/2022 20:01
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @GetMapping("/status/check")
    public String getStatus(){
        return "Available";
    }
}
