package com.nene.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @ClassName JwtUtil
 * @Description Jwt工具类
 * @Author Protip
 * @Date 2023/1/7 17:34
 * @Version 1.0
 */
public class JwtUtil {

    /**
     * TOKEN的有效期(ms)
     */
    private static final int TOKEN_TIME_OUT = 7000 * 1000;

    /**
     * 加密KEY
     */
    private static final String TOKEN_ENTRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";

    /**
     * 最小刷新间隔(ms)
     */
    private static final int REFRESH_TIME = 300 * 1000;

    /**
     * 生成Token
     */
    public static String getToken(Long id) {
        Map<String, Object> claimMaps = new HashMap<>(2);
        claimMaps.put("id", id);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                //签发时间
                .setIssuedAt(new Date(currentTime))
                //说明
                .setSubject("system")
                //签发者信息
                .setIssuer("nene")
                //接收用户
                .setAudience("app")
                //数据压缩方式
                .compressWith(CompressionCodecs.GZIP)
                //加密方式
                .signWith(SignatureAlgorithm.HS512, generalKey())
                //过期时间戳
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT))
                //cla信息
                .addClaims(claimMaps)
                .compact();
    }

    /**
     * 获取token中的claims信息
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * 获取header body信息
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public static int verifyToken(Claims claims) {
        if (claims == null) {
            return 1;
        }
        try {
            claims.getExpiration()
                    .before(new Date());
            // 需要自动刷新TOKEN
            if ((claims.getExpiration().getTime() - System.currentTimeMillis()) > REFRESH_TIME) {
                return -1;
            } else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    /**
     * 由字符串生成加密key
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENTRY_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
