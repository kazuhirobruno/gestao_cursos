package br.com.kazuhiro.gestao_cursos.modules.student.useCases;

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

import br.com.kazuhiro.gestao_cursos.modules.student.dtos.AuthStudentRequestDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.dtos.AuthStudentResponseDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.repository.StudentRepository;

@Service
public class AuthStudentUseCase {
  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${security.token.secret.student}")
  private String secretKey;

  public AuthStudentResponseDTO execute(AuthStudentRequestDTO authStudentRequestDTO) throws AuthenticationException {
    var student = this.studentRepository.findByUsername(authStudentRequestDTO.username()).orElseThrow(
        () -> {
          throw new UsernameNotFoundException("Invalid username or password");
        });

    var isPasswordValid = this.passwordEncoder.matches(authStudentRequestDTO.password(), student.getPassword());

    if (!isPasswordValid) {
      throw new AuthenticationException("Invalid username or password");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(60));
    var roles = Arrays.asList("STUDENT");
    var token = JWT.create()
        .withSubject(student.getId().toString())
        .withExpiresAt(expiresIn)
        .withClaim("name", student.getName())
        .withClaim("roles", roles)
        .sign(algorithm);

    var authStudentResponseDTO = AuthStudentResponseDTO.builder()
        .token(token)
        .expiresIn(expiresIn.toEpochMilli())
        .roles(roles)
        .build();

    return authStudentResponseDTO;
  }
}
