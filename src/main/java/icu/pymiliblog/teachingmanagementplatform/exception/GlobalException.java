package icu.pymiliblog.teachingmanagementplatform.exception;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    /**
     * 异常处理器
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> ex(Exception ex) {
        log.warn("Exception handler: {}", ex.toString());
        return ApiResponse.error("服务错误！请联系管理员！");
    }
}
