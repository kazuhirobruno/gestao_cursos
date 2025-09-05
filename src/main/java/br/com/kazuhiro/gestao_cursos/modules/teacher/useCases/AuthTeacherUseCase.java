package br.com.kazuhiro.gestao_cursos.modules.teacher.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.AuthTeacherRequestDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.AuthTeacherResponseDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.repository.TeacherRepository;

@Service
public class AuthTeacherUseCase {

  @Autowired
  private TeacherRepository teacherRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${security.token.secret.teacher}")
  private String secretKey;

  public AuthTeacherResponseDTO execute(AuthTeacherRequestDTO authTeacherRequestDTO) throws AuthenticationException {
    var teacher = teacherRepository.findByUsername(authTeacherRequestDTO.username()).orElseThrow(
        () -> {
          throw new UsernameNotFoundException("Invalid username or password");
        });

    var isPasswordValid = this.passwordEncoder.matches(authTeacherRequestDTO.password(), teacher.getPassword());

    if (!isPasswordValid) {
      throw new AuthenticationException("Invalid username or password");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(60));
    var roles = Arrays.asList("TEACHER");
    var token = JWT.create()
        .withSubject(teacher.getId().toString())
        .withExpiresAt(expiresIn)
        .withClaim("name", teacher.getName())
        .withClaim("roles", roles)
        .sign(algorithm);

    var authTeacherResponseDTO = AuthTeacherResponseDTO.builder()
        .token(token)
        .expiresIn(expiresIn.toEpochMilli())
        .roles(roles)
        .build();

    return authTeacherResponseDTO;
  }

}
