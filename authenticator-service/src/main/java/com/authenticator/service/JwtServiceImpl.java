package com.authenticator.service;

import com.authenticator.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private KeyStore keyStore;

    @Getter
    @Value("${jwt.expiration.time}")
    private Long expirationTime;

    @PostConstruct
    public void init() {

        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/authentication.jks");
            keyStore.load(resourceAsStream, "123456".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new CustomException("Exception occurred while loading keystore");
        }
    }

    @Override
    public String generateToken(Authentication authentication) {

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(expirationTime)))
                .compact();
    }

    @Override
    public String generateTokenWithUsername(String username) {

        return Jwts.builder()
                .setSubject(username)
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(expirationTime)))
                .compact();
    }

    @Override
    public boolean validateToken(String jwt) {

        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);

        return true;
    }

    @Override
    public String getUsernameFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("authentication", "123456".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new CustomException("Exception occurred while retrieving public key from keystore");
        }
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("authentication").getPublicKey();
        } catch (KeyStoreException e) {
            throw new CustomException("Exception occurred while retrieving public key from keystore");
        }
    }
}
