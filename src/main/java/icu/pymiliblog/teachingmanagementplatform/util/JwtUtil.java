package icu.pymiliblog.teachingmanagementplatform.util;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // 令牌有效期（毫秒）
    private static final long VALIDITY_PERIOD_MILLIS = 3600000; // 1小时
    // secret key
    private static final String secretKey = "0Fx9WeAzPOKrC26GDEIROVaapihmFYmeTk/YIKkcDLs=";
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 生成随机密钥
    public static String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256位密钥
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    // 生成JWT
    public static String createJwt(String username, int userId) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + VALIDITY_PERIOD_MILLIS);

        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuer("auth0")
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    // 解析JWT
    public static Map<String, Object> parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        Map<String, Object> result = new HashMap<>();

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            Claims claims = claimsJws.getPayload();
            result.put("username", claims.getSubject());
            result.put("userId", claims.get("userId", Integer.class));
            return result;
        } catch (ExpiredJwtException e) {
            // 令牌已过期
            return result;
        } catch (UnsupportedJwtException e) {
            // 令牌格式不支持
            // throw new RuntimeException("Unsupported JWT", e);
            return result;
        } catch (MalformedJwtException e) {
            // 令牌格式错误
            // throw new RuntimeException("Invalid JWT token", e);
            return result;
        } catch (SignatureException e) {
            // 签名验证失败
            // throw new RuntimeException("Invalid JWT token signature", e);
            return result;
        } catch (IllegalArgumentException e) {
            // 令牌为空或null
            // throw new RuntimeException("JWT token compact of handler are invalid", e);
            return result;
        }
    }

    public static boolean verifyJwt(String jwt) {
        Map<String, Object> jwtMap = JwtUtil.parseToken(jwt);
        logger.info(jwtMap.toString());
        return jwtMap.isEmpty();
    }

    public static String extractJwt(String authorizationHeader) {
        // 提取 JWT
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7); // 移除 "Bearer " 前缀
    }

    public static void test() {
        // 生成JWT
        String token = createJwt("admin", 1);
        System.out.println("Generated JWT: " + token);

        // 解析JWT
        Map<String, Object> claims = parseToken(token);
        System.out.println("Username: " + claims.get("username"));
        System.out.println("User ID: " + claims.get("userId"));
    }
}