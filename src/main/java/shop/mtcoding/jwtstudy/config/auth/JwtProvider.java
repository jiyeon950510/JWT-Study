package shop.mtcoding.jwtstudy.config.auth;

import java.sql.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.jwtstudy.model.User;

public class JwtProvider {
    private static final String SUBJECT = "jwtstudy";
    private static final int EXP = 1000 * 60 * 60;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization"; // 헤더는 밖에서 접근할 수 있음
    private static final String SECRET = "메타코딩";

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP)) // 현재시간 + 일주일 (초, 분, 시,
                                                                           // 일, 일주일)
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                // 대칭키, 공개키
                .sign(Algorithm.HMAC512("메타코딩")); // 절대 노출되면 안됨
        System.out.println(jwt);
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩"))
                .build().verify(jwt);
        // int id = decodedJWT.getClaim("id").asInt();
        // String role = decodedJWT.getClaim("role").asString();
        // System.out.println(id);
        // System.out.println(role);
        return decodedJWT;

    }

}
