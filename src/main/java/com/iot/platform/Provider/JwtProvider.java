package com.iot.platform.Provider;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Core.System.UserAuth;
import com.iot.platform.Enum.System.RoleEnum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secretKey;

    @Value("${security.jwt.expiration:#{24*60*60*1000}}")
    private long validityInMilliseconds = 3600000;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(SystemUser systemUser) {
        Claims claims = Jwts.claims().setSubject(systemUser.getId());
        claims.put("roleEnum", systemUser.getRoleEnum());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        UserAuth userAuth = new UserAuth(claims.getSubject(), "", true, true, true, true,
                Arrays.asList(RoleEnum.valueOf("ROLE_ADMIN")));
        userAuth.setClaims(claims);
        return new UsernamePasswordAuthenticationToken(userAuth, "", userAuth.getAuthorities());
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new Exception("Expired or invalid JWT token");
        }
    }

}