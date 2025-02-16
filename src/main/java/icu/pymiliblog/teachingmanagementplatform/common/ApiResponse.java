package icu.pymiliblog.teachingmanagementplatform.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 接口响应封装
 * @author PYmili
 * @param <T>
 */
public class ApiResponse<T> {
    private int code;
    private String msg;
    private Object data;

    public ApiResponse() {}

    public ApiResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回错误
     * @param data {@link Object}
     * @return {@link ApiResponse}
     */
    public static ResponseEntity<ApiResponse<Object>> not_found(Object data) {
        ApiResponse<Object> notFound = new ApiResponse<>(
            HttpStatus.NOT_FOUND.value(), "not found", data);
        return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
    }

    /**
     * 返回成功
     * @param data {@link Object}
     * @return {@link ApiResponse}
     */
    public static ResponseEntity<ApiResponse<Object>> ok(Object data) {
        ApiResponse<Object> success = new ApiResponse<>(
                200, "success", data);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    /**
     * 服务器错误
     * @param data {@link Object}
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<ApiResponse<Object>> error(Object data) {
        ApiResponse<Object> error = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", data);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 非法请求
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<ApiResponse<Object>> illegal() {
        return ApiResponse.not_found("非法请求！");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
