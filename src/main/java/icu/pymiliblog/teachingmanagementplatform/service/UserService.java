package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.UserMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserPojo;
import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserRequestPojo;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import icu.pymiliblog.teachingmanagementplatform.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

/**
 * 用户 Service
 * @author PYmili
 */
@Slf4j
@Service
public class UserService {

    // 操作用户的Mapper
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 注册用户
     * @param requestPojo {@link UserRequestPojo}
     * @return {@link ResponseEntity}
     * @throws NoSuchAlgorithmException
     */
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
        boolean registeredResult = userMapper.upload(userPojo);
        if (!registeredResult) {
            return ApiResponse.not_found("注册失败！");
        }

        // 生成jwt
        String jwt = JwtUtil.createJwt(userPojo.getUsername(), userPojo.getId());
        return ApiResponse.ok(jwt);
    }

    /**
     * 用户登录
     * @param requestPojo {@link UserRequestPojo}
     * @return {@link ResponseEntity}
     * @throws NoSuchAlgorithmException
     */
    public ResponseEntity<ApiResponse<Object>> login(UserRequestPojo requestPojo)
            throws NoSuchAlgorithmException {
        UserPojo userPojo = new UserPojo();
        userPojo.setPhoneNumber(requestPojo.getPhoneNumber());
        userPojo.setEmail(requestPojo.getEmail());
        userPojo.setQqId(requestPojo.getQqId());
        userPojo.setUsername(requestPojo.getUsername());
        log.info("[UserService] [login] find userPojo: {}", userPojo);
        // 查找用户是否存在
        List<UserPojo> findResult = userMapper.findByPojo(userPojo);
        if (findResult.isEmpty()) {
            return ApiResponse.not_found("用户不存在！");
        }
        log.info("[UserService] [login] find result: {}", findResult);

        // 处理密码
        String passwordHash = requestPojo.getPassword();
        String salt = findResult.getFirst().getSalt();
        log.info("[UserService] [login] request password value: {} ", passwordHash);
        String hashPassword = PasswordUtil.hashPassword(passwordHash, salt);
        log.info("[UserService] [login] hash password value: {}", hashPassword);
        if (!Objects.equals(findResult.getFirst().getPasswordHash(), hashPassword)) {
            return ApiResponse.not_found("用户或密码错误！");
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
            log.warn("更新 salt，SecretKey，password_hash 时失败！ userPojo: {}", userPojo);
            return ApiResponse.not_found("系统错误！");
        }

        return ApiResponse.ok(jwt);
    }
}
