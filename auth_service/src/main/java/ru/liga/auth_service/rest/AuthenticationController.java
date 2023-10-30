package ru.liga.auth_service.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.auth_service.dto.RegDto;
import ru.liga.auth_service.service.UserService;


@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody RegDto request) {
        return userService.createUser(request);
    }

    @RequestMapping("/login")
    public String login() {
        return "redirect:/oauth2/authorization/{registrationId}";
    }
}