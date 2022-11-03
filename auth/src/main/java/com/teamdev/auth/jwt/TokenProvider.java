package com.teamdev.auth.jwt;

import com.teamdev.auth.exception.ErrorMessage;
import com.teamdev.auth.exception.ForbiddenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String HTTP_AUTHORIZATION_SCHEME_BEARER = "Bearer";
    private static final String DEVICE_KEY = "device";

    private final String secret;
    private final long tokenValidityInMilliseconds;

    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String device) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim(DEVICE_KEY, device)
                .compact();
    }

    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HTTP_AUTHORIZATION_SCHEME_BEARER)) {
            return bearerToken.substring(7);
        }

        log.debug("토큰을 찾을 수 없습니다.");
        return null;
    }

    public boolean validateToken(String token, String device) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String deviceInClaim = claims.get(DEVICE_KEY).toString();

            if (!device.equals(deviceInClaim)) {
                throw new ForbiddenException(ErrorMessage.DEVICE_FORBIDDEN);
            }

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
