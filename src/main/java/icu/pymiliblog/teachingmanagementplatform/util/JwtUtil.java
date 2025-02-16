package icu.pymiliblog.teachingmanagementplatform.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT的工具类
 * @author PYmili
 */
@Slf4j
public class JwtUtil {

    // 令牌有效期（毫秒）
    private static final long VALIDITY_PERIOD_MILLIS = 3600000; // 1小时
    // secret key
    private static final String secretKey = "0Fx9WeAzPOKrC26GDEIROVaapihmFYmeTk/YIKkcDLs=";

    /**
     * 生成随机密钥
     * @return {@link String}
     */
    public static String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256位密钥
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * 生成JWT
     * @param username {@link String}
     * @param userId int {@link Integer}
     * @return {@link String}
     */
    public static String createJwt(String username, Integer userId) {
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

    /**
     * 解析JWT
     * @param token {@link String}
     * @return {@link Map}
     */
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
            throw new RuntimeException("Expired Jwt", e);
        } catch (UnsupportedJwtException e) {
            // 令牌格式不支持
            throw new RuntimeException("Unsupported JWT", e);
        } catch (MalformedJwtException e) {
            // 令牌格式错误
            throw new RuntimeException("Invalid JWT token", e);
        } catch (SignatureException e) {
            // 签名验证失败
            throw new RuntimeException("Invalid JWT token signature", e);
        } catch (IllegalArgumentException e) {
            // 令牌为空或null
            throw new RuntimeException("JWT token compact of handler are invalid", e);
        }
    }

    /**
     * 验证JWT
     * @param auth {@link String}
     * @return boolean
     */
    public static boolean verifyJwt(String auth) {
        String jwt = extractJwt(auth);
        if (jwt == null || jwt.isEmpty()) return false;
        // parse jwt
        try {
            Map<String, Object> jwtMap = JwtUtil.parseToken(jwt);
            log.info(jwtMap.toString());
            return !jwtMap.isEmpty();
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    /**
     * 解析JWT
     * @param authorizationHeader {@link String}
     * @return {@link String}
     */
    public static String extractJwt(String authorizationHeader) {
        // 提取 JWT
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7); // 移除 "Bearer " 前缀
    }

    /**
     * 测试
     * @author PYmili
     */
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