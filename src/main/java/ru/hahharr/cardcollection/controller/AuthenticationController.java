package ru.hahharr.cardcollection.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.hahharr.cardcollection.models.orm.UserOrm;
import ru.hahharr.cardcollection.repository.interfaces.UserRepository;
import ru.hahharr.cardcollection.models.dto.LoginRequestDto;
import ru.hahharr.cardcollection.models.dto.TokenResponseDto;
import ru.hahharr.cardcollection.security.service.AuthenticationService;

import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registration")
    public HttpStatus register(@RequestBody LoginRequestDto registrationDto) {
        Optional<UserOrm> user = userRepository.findByUsername(registrationDto.getUsername());

        if (user.isPresent()) {
            return HttpStatus.BAD_REQUEST;
        }
        return authenticationService.register(registrationDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<TokenResponseDto> refreshToken(HttpServletRequest request) {
        return authenticationService.refreshToken(request);
    }
}
