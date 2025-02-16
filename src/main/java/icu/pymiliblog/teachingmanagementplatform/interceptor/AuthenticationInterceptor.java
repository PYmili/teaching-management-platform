package icu.pymiliblog.teachingmanagementplatform.interceptor;

import com.alibaba.fastjson2.JSONObject;
import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 身份验证拦截器
 * @author PYmili
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * Controller执行之前
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @throws IOException response writer
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String remoteAddr = request.getRemoteAddr();
        StringBuffer requestURL = request.getRequestURL();
        log.info("Authentication interceptor: remote address: {}," +
                " request URL: {}", remoteAddr, requestURL);

        String authorization = request.getHeader("Authorization");
        log.info("authorization string: {}", authorization);
        if (!JwtUtil.verifyJwt(authorization)) {
            log.warn("Authorization fail!");
            String responseJson = JSONObject.toJSONString(ApiResponse.illegal());
            response.getWriter().write(responseJson);
            return false;
        }

        log.info("Authorization success!");
        return true;
    }

    /**
     * Controller执行之后，视图渲染之前
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @param modelAndView {@link ModelAndView}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {}

    /**
     * 拦截器结束后
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @param ex {@link Exception}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {}
}
