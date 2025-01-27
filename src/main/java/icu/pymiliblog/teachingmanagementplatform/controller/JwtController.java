package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> verify(
            @RequestParam(value = "jwt", required = false) String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数残缺"), HttpStatus.NOT_FOUND);
        }
        boolean verifyJwt = JwtUtil.verifyJwt(jwt);
        String returnString = "success";
        if (verifyJwt) {
            returnString = "error";
        }
        return new ResponseEntity<>(
                returnString.equals("success") ? ApiResponse.ok(returnString)
                        : ApiResponse.not_found(returnString),
                returnString.equals("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
