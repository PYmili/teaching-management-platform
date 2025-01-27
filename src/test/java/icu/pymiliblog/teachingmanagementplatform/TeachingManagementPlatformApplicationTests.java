package icu.pymiliblog.teachingmanagementplatform;

import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import icu.pymiliblog.teachingmanagementplatform.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

@SpringBootTest
class TeachingManagementPlatformApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void HashPasswordTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = PasswordUtil.generateSalt();
        String password = "123";
        String hashResult = PasswordUtil.hashPassword(password, salt);
        System.out.println("pwd: " + password);
        System.out.println("salt: " + salt);
        System.out.println("result: " + hashResult);
    }

    @Test
    void testJwt() {
        JwtUtil.test();
    }

    @Test
    void testGenSk() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256位密钥
        random.nextBytes(keyBytes);
        System.out.println(Base64.getEncoder().encodeToString(keyBytes));
    }
}
