package com.wx.manage.until;

import com.wx.manage.exception.GlobalException;
import com.wx.manage.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    @Autowired
    public static HttpServletRequest request;

    //token过期时间
    private static final long TOKEN_EXPIRATION = 24*60*60*1000;

    //token密钥
    private static final String TOKEN_SIGN_KEY = "wenxiang_manage";

    private static Key getKeyInstance(){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(TOKEN_SIGN_KEY);
        return new SecretKeySpec(bytes,signatureAlgorithm.getJcaName());
    }

    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("wx-manage")
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, getKeyInstance())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 判断token是否有效
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        if(StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(org.springframework.util.StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Long getUserId() {
        return getUserId(request);
    }


    public static Long getUserId(String token) {
        Claims claims = getClaims(token);

        return (Long)claims.get("userId");
    }

    /**
     * 根据token字符串获取会员id
     * @param request
     * @return
     */
    public static Long getUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = getClaims(token);
        return (Long)claims.get("userId");
    }

    public static String getUserName(String token) {
        Claims claims = getClaims(token);
        return (String)claims.get("userName");
    }

    /**
     * 根据token字符串获取会员id
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = getClaims(token);
        return (String)claims.get("userName");
    }

    public static void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

    /**
     * 校验token并返回Claims
     * @param token
     * @return
     */
    private static Claims getClaims(String token) {
        if(StringUtils.isEmpty(token)) {
            // LOGIN_FAIL(-211, "未登录"),
            throw new GlobalException(ResultCodeEnum.TOKEN_PARSE_FAIL);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims;
        } catch (Exception e) {
            throw new GlobalException(ResultCodeEnum.TOKEN_PARSE_FAIL);
        }
    }
}







