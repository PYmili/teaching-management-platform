package icu.pymiliblog.teachingmanagementplatform.util;


import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 密码操作的工具类
 * @author PYmili
 */
@Component
public class PasswordUtil {
    private static final int SALT_LENGTH = 16; // 盐的长度为16字节
    private static final int ITERATIONS = 65536; // PBKDF2算法执行的迭代次数
    private static final int KEY_LENGTH = 256; // 密钥长度

    /**
     * 将密码hash处理
     * @param password {@link String} 密码
     * @param salt 盐
     * @return {@link String}
     * @throws NoSuchAlgorithmException
     */
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] saltBytes = hexToBytes(salt);
        byte[] passwordBytes = password.getBytes();
        byte[] combined = new byte[saltBytes.length + passwordBytes.length];
        System.arraycopy(saltBytes, 0, combined, 0, saltBytes.length);
        System.arraycopy(passwordBytes, 0, combined, saltBytes.length, passwordBytes.length);
        byte[] hashBytes = md.digest(combined);
        return bytesToHex(hashBytes);
    }

    /**
     * 生成hash盐
     * @return {@link String}
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    /**
     * 字节转16进制
     * @param bytes byte[]
     * @return {@link String}
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 16进制转字节
     * @param hexString {@link String}
     * @return byte[]
     */
    private static byte[] hexToBytes(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int intValue = Integer.parseInt(hexString.substring(index, index + 2), 16);
            bytes[i] = (byte) (intValue & 0xff);
        }
        return bytes;
    }
}
