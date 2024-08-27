package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.CredenciaisContaDTO;
import com.pi.mafu_bakery_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody CredenciaisContaDTO data) throws Exception {
        if (checkIfParamsIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.authentication(data);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    private boolean checkIfParamsIsNotNull(CredenciaisContaDTO data) {
        return data == null || data.getEmail() == null || data.getEmail().isBlank()
                || data.getSenha() == null || data.getSenha().isBlank();
    }
}
