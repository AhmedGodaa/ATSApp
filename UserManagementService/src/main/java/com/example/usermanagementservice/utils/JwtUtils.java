package com.example.usermanagementservice.utils;

import com.example.usermanagementservice.models.RefreshToken;
import com.example.usermanagementservice.repositories.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.example.usermanagementservice.common.Constants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${ClientService.app.jwtSecret}")
    private String jwtSecret;
    @Value("${ClientService.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${ClientService.app.jwtRefreshSecret}")
    private String jwtRefreshSecret; // Separate secret for refresh tokens

    @Value("${ClientService.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs; // Refresh token expiration time

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String getJwtFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        } else {
            return null;
        }
    }

    public String generateJwtToken(String email) {
//        this username is the email
        return generateTokenFromEmail(email, jwtSecret, jwtExpirationMs);
    }

    // Generate Refresh Token
    public String generateRefreshToken(String email) {
        refreshTokenRepository.deleteByEmail(email);
        String refreshToken = generateRefreshTokenFromEmail(email, jwtRefreshSecret); // No expiration for refresh token
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setEmail(email);
        refreshTokenRepository.save(tokenEntity);
        return refreshToken;
    }

    public String generateTokenFromEmail(String email, String jwtSecret, int expiration) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.builder()
                .claims()
                .add("email", email)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(key, Jwts.SIG.HS256)
                .compact();


    }

    public String generateRefreshTokenFromEmail(String email, String jwtSecret) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.builder()
                .claims()
                .add("email", email)
                .subject(email)
                .and()
                .signWith(key, Jwts.SIG.HS256)
                .compact();


    }

    // Validate Access Token
    public boolean validateJwtToken(String authToken) {
        return validateToken(authToken, jwtSecret);
    }



    // Validate Refresh Token with Database Check
    public void validateRefreshToken(String token) {
        // Check if the token exists in the database
        //   this will ensure the token is not used before
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token Used Before or Invalid"));
        if (!refreshToken.getEmail().equals(getEmailFromToken(token, jwtRefreshSecret))) {
            throw new RuntimeException("Email does not match the token");
        }
        if (!validateToken(token, jwtRefreshSecret)) {
            throw new RuntimeException("Token is Used Before or Invalid");
        }

    }


    // Token validation logic
    private boolean validateToken(String token, String secret) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new SignatureException(e.getMessage());

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException(e.getMessage());

        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new ExpiredJwtException(null, null, e.getMessage());

        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new UnsupportedJwtException(e.getMessage());

        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());

        }
    }


    public String getEmailFromJwtToken(String token) {
        return getEmailFromToken(token, jwtSecret);
    }

    public String getEmailFromRefreshToken(String token) {
        return getEmailFromToken(token, jwtRefreshSecret);
    }


    public String getEmailFromToken(String token, String secret) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }
}






