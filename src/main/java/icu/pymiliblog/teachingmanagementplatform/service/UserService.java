package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.UserMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserPojo;
import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserRequestPojo;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import icu.pymiliblog.teachingmanagementplatform.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    public ResponseEntity<ApiResponse<Object>> register(UserRequestPojo requestPojo)
            throws NoSuchAlgorithmException {
        UserPojo userPojo = new UserPojo();
        userPojo.setUsername(requestPojo.getUsername());
        userPojo.setPhoneNumber(requestPojo.getPhoneNumber());
        userPojo.setEmail(requestPojo.getEmail());
        userPojo.setQqId(requestPojo.getQqId());
        // 生成hash的盐
        String salt = PasswordUtil.generateSalt();
        userPojo.setSalt(salt);
        // 对密码和盐hash处理
        String passwordHash = PasswordUtil.hashPassword(requestPojo.getPassword(), salt);
        userPojo.setPasswordHash(passwordHash);
        // mapper
        boolean registeredResult = userMapper.register(userPojo);
        if (!registeredResult) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("注册失败！"),
                    HttpStatus.NOT_FOUND
            );
        }
        // 生成jwt
        String jwt = JwtUtil.createJwt(userPojo.getUsername(), userPojo.getId());
        return new ResponseEntity<>(
                ApiResponse.ok(jwt),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ApiResponse<Object>> login(UserRequestPojo requestPojo)
            throws NoSuchAlgorithmException {
        UserPojo userPojo = new UserPojo();
        userPojo.setPhoneNumber(requestPojo.getPhoneNumber());
        userPojo.setEmail(requestPojo.getEmail());
        userPojo.setQqId(requestPojo.getQqId());
        userPojo.setUsername(requestPojo.getUsername());
        logger.info("[UserService] [login] find userPojo: {}", userPojo);
        // 查找用户是否存在
        List<UserPojo> findResult = userMapper.find(userPojo);
        if (findResult.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("用户不存在！"),
                    HttpStatus.NOT_FOUND
            );
        }
        logger.info("[UserService] [login] find result: {}", findResult);

        // 处理密码
        String passwordHash = requestPojo.getPassword();
        String salt = findResult.getFirst().getSalt();
        logger.info("[UserService] [login] request password value: {} ", passwordHash);
        String hashPassword = PasswordUtil.hashPassword(passwordHash, salt);
        logger.info("[UserService] [login] hash password value: {}", hashPassword);
        if (!Objects.equals(findResult.getFirst().getPasswordHash(), hashPassword)) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("用户或密码错误！"),
                    HttpStatus.NOT_FOUND
            );
        }

        // 生成jwt
        String jwt = JwtUtil.createJwt(
                findResult.getFirst().getUsername(),
                findResult.getFirst().getId());

        // 更新salt和password_hash
        String newSalt = PasswordUtil.generateSalt();
        boolean updateSaltResult = userMapper.updateSalt(findResult.getFirst().getId(), newSalt);
        String newPasswordHash = PasswordUtil.hashPassword(requestPojo.getPassword(), newSalt);
        boolean updatePasswordHash = userMapper.updatePasswordHash(findResult.getFirst().getId(),
                newPasswordHash);
        if (!updateSaltResult || !updatePasswordHash) {
            logger.warn("更新 salt，SecretKey，password_hash 时失败！ userPojo: {}", userPojo);
            return new ResponseEntity<>(
                    ApiResponse.not_found("系统错误！"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return new ResponseEntity<>(
                ApiResponse.ok(jwt),
                HttpStatus.OK
        );
    }
}
