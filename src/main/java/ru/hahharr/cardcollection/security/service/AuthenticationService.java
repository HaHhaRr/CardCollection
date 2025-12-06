package ru.hahharr.cardcollection.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.repository.interfaces.UserRepository;
import ru.hahharr.cardcollection.security.Role;
import ru.hahharr.cardcollection.security.dto.LoginRequestDto;
import ru.hahharr.cardcollection.security.dto.TokenResponseDto;
import ru.hahharr.cardcollection.security.jwt.JwtService;
import ru.hahharr.cardcollection.security.user.details.CustomUserDetailService;

@Service
public class AuthenticationService {

    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${security.jwt.refresh_token_expiration}")
    private long refreshTokenExpiration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    public HttpStatus register(LoginRequestDto request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        return HttpStatus.OK;
    }

    public TokenResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getUsername());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    public ResponseEntity<TokenResponseDto> refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());

        if (jwtService.getExpirationTime(token) != refreshTokenExpiration) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtService.extractUsername(token);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

        if (jwtService.isValidToken(token, userDetails)) {

            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            return new ResponseEntity<>(new TokenResponseDto(accessToken, refreshToken), HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
