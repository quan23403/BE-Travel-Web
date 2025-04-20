package com.example.Travel_web.BE.util;


import com.example.Travel_web.BE.dto.request.LoginDTO;
import com.example.Travel_web.BE.dto.response.LoginResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtils {

    private final JwtDecoder jwtDecoder;
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
    @Value("${jwt.signerKey}")
    private String jwtKey;

    @Value("${jwt.token-validity-in-second}")
    private Long expiration;

    public JwtUtils(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }
    public String createToken(LoginResponseDTO.User user) throws JsonProcessingException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("hmquan")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("user", user)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(jwtKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token); // Giải mã token
    }

    public Long getUserIdFromToken(String token) throws Exception {
        try {
            // Remove the "Bearer " prefix from the token, if present
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                throw new Exception("Invalid token format.");
            }
            Jwt jwt = decodeToken(token);
            Map<String, Object> userMap = jwt.getClaim("user");
            if (userMap != null && userMap.containsKey("id")) {
                return (Long) userMap.get("id");
            }
        } catch (Exception e) {
            throw new Exception("Failed to process the token: " + e.getMessage());
        }
        return null;
    }
}
