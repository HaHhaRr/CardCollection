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
import ru.hahharr.cardcollection.models.dto.request.LoginRequestDto;
import ru.hahharr.cardcollection.models.dto.response.TokenResponseDto;
import ru.hahharr.cardcollection.models.orm.UserCoinStateOrm;
import ru.hahharr.cardcollection.models.orm.UserCollectionOrm;
import ru.hahharr.cardcollection.models.orm.UserOrm;
import ru.hahharr.cardcollection.repository.interfaces.UserRepository;
import ru.hahharr.cardcollection.security.Role;
import ru.hahharr.cardcollection.security.jwt.JwtService;
import ru.hahharr.cardcollection.security.user.details.CustomUserDetailService;
import ru.hahharr.cardcollection.utils.provider.LocalDateTimeProvider;

import java.util.Collections;

@Service
public class AuthenticationService {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final int INITIAL_NUMBER_OF_COINS = 5000;

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

    public ResponseEntity<HttpStatus> register(LoginRequestDto request) {
        UserOrm userOrm = new UserOrm(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Role.ROLE_USER);

        userOrm.setUserCoinStateOrm(new UserCoinStateOrm(
                INITIAL_NUMBER_OF_COINS,
                LocalDateTimeProvider.moscow(),
                userOrm));

        userOrm.setUserCollectionOrm(new UserCollectionOrm(
                userOrm,
                Collections.emptyList()));

        userRepository.save(userOrm);

        return new ResponseEntity<>(HttpStatus.OK);
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
