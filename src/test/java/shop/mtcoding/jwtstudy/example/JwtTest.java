package shop.mtcoding.jwtstudy.example;

import static org.mockito.ArgumentMatchers.nullable;

import java.security.SignatureException;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

//JSON Web Token
public class JwtTest {

    @Test
    public void createdJwt_test() {
        // given

        // when
        String jwt = JWT.create()
                .withSubject("토큰제목")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 현재시간 + 일주일 (초, 분, 시,
                                                                                               // 일, 일주일)
                .withClaim("id", 1)
                .withClaim("role", "guest")
                // 대칭키, 공개키
                .sign(Algorithm.HMAC512("메타코딩")); // 절대 노출되면 안됨
        System.out.println(jwt);
        // when
        // then
    }

    @Test
    public void verifyJwt_test() {
        // given
        String jwt = JWT.create()
                .withSubject("토큰제목")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 현재시간 + 일주일 (초, 분, 시,
                                                                                               // 일, 일주일)
                .withClaim("id", 1)
                .withClaim("role", "guest")
                // 대칭키, 공개키
                .sign(Algorithm.HMAC512("메타코딩")); // 절대 노출되면 안됨
        System.out.println(jwt);
        // when
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩"))
                    .build().verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();
            System.out.println(id);
            System.out.println(role);

        } catch (SignatureVerificationException sve) {
            System.out.println("토큰 검증 실패" + sve.getMessage());
        } catch (TokenExpiredException tee) {
            System.out.println("토큰 검증 실패" + tee.getMessage());

        }

    }
    // then
}
