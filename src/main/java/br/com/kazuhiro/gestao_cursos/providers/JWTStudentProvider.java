package br.com.kazuhiro.gestao_cursos.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTStudentProvider {

  @Value("${security.token.secret.student}")
  private String secretKey;

  public DecodedJWT validateToken(String token) {
    token = token.replace("Bearer ", "");
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    try {
      var jwt = JWT.require(algorithm)
          .build()
          .verify(token);
      return jwt;
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return null;
    }
  }
}
