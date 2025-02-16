package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> verify(@RequestBody String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return ApiResponse.not_found("参数残缺");
        }
        boolean verifyJwt = JwtUtil.verifyJwt(jwt);
        String returnString = "success";
        if (verifyJwt) {
            returnString = "error";
        }
        return returnString.equals("success")
                ? ApiResponse.ok(returnString) : ApiResponse.not_found(returnString);
    }
}
