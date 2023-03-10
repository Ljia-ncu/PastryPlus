package org.mrl.component;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.mrl.constant.Constants;
import org.mrl.property.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.Date;
import java.util.Map;

public class JwtTokenManager {

    @Autowired
    private JwtProperties properties;

    public String createToken(Map<String, Object> payload) {
        Date date = new Date();
        payload.put(JWTPayload.ISSUED_AT, date);

        Date expiredDate = DateUtil.offsetDay(date, properties.getExpireDays());
        payload.put(JWTPayload.EXPIRES_AT, expiredDate);

        return JWTUtil.createToken(payload, properties.getKey().getBytes());
    }

    public String resolveToken(HttpServletRequest request) {
        String tokenPrefix = Constants.JWT.TOKEN_PREFIX;
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isNotBlank(token) && token.startsWith(tokenPrefix)) {
            return token.substring(tokenPrefix.length()).trim();
        }
        return null;
    }

    public Object getPayload(String token, String key) {
        JWT jwt = JWTUtil.parseToken(token);
        if (jwt.setKey(properties.getKey().getBytes()).verify() && jwt.validate(0)) {
            return jwt.getPayload().getClaim(key);
        }
        return null;
    }
}
