package com.example.demo.controller;

import com.example.demo.util.ValidatePass;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.nimbusds.jose.shaded.gson.Gson;

@RestController
@RequestMapping("/validate")
@CrossOrigin(origins = "http://localhost:4200")
public class ValidatePassController {
    private static final Gson gson = new Gson();

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> validatePass(@RequestBody String password) {
        if(ValidatePass.isValid(password)) {
            return ResponseEntity.ok(gson.toJson(true));
        } else {
            return ResponseEntity.ok().body(gson.toJson(false));
        }
    }
}
