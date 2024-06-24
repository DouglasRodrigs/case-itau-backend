package com.example.demo.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.MultiValueMap;
import com.nimbusds.jose.shaded.gson.Gson;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/token")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TokenController {
    private static final Gson gson = new Gson();

    @PostMapping("/")
    public ResponseEntity<String> token(@RequestBody User user) {

        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", user.clientId);
        formData.add("username", user.username);
        formData.add("password", user.password);
        formData.add("grant_type", user.grantType);

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(formData, headers);

        var result = rt.postForEntity("http://localhost:8080/realms/caseItau/protocol/openid-connect/token", entity, String.class);
        return ResponseEntity.ok(gson.toJson(result.getBody()));
    }

    public record User(String password, String clientId, String grantType, String username) {}
}
