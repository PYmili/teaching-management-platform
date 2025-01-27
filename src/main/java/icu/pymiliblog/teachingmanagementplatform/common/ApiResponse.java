package icu.pymiliblog.teachingmanagementplatform.common;

public class ApiResponse<T> {
    public int status;
    public Object data;

    /**
     * 返回错误
     * @param data {@link Object}
     * @return {@link ApiResponse}
     */
    public static ApiResponse<Object> not_found(Object data) {
        return new ApiResponse<>(404, data);
    }

    /**
     * 返回成功
     * @param data {@link Object}
     * @return {@link ApiResponse}
     */
    public static ApiResponse<Object> ok(Object data) {
        return new ApiResponse<>(200, data);
    }

    public static ApiResponse<Object> illegal() {
        return ApiResponse.not_found("非法请求！");
    }

    public ApiResponse() {}

    public ApiResponse(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
