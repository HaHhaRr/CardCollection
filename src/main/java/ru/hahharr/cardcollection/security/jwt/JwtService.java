package ru.hahharr.cardcollection.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.security.user.details.CustomUserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.secret_key}")
    private String secretKey;

    @Value("${security.jwt.access_token_expiration}")
    private long accessTokenExpiration;

    @Value("${security.jwt.refresh_token_expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiryTime) {
        long time = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .claims()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(time))
                .expiration(new Date(time + expiryTime))
                .add(extraClaims)
                .and()
                .signWith(getSigningKey());

        return builder.compact();
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        extraClaims.put("id", customUserDetails.getUser().getId());
        extraClaims.put("role", customUserDetails.getUser().getRole());
        return generateToken(extraClaims, userDetails, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(null, userDetails, refreshTokenExpiration);
    }

    public long getExpirationTime(String token) {
        long expiration = extractAllClaims(token).getExpiration().getTime();
        long issuedAt = extractAllClaims(token).getIssuedAt().getTime();

        return Math.abs(expiration - issuedAt);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return !expiration.before(new Date());
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && isTokenExpired(token);
    }
}
