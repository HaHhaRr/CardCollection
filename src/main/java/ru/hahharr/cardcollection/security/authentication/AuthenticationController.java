package ru.hahharr.cardcollection.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.repository.UserRepository;
import ru.hahharr.cardcollection.security.dto.LoginRequestDto;
import ru.hahharr.cardcollection.security.dto.TokenResponseDto;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registration")
    public ResponseEntity<TokenResponseDto> register(@RequestBody LoginRequestDto registrationDto) {
        User user = userRepository.findByUsername(registrationDto.getUsername())
                .orElse(null);

        if (user != null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(authenticationService.register(registrationDto));
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
