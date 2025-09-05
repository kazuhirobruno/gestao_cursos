package br.com.kazuhiro.gestao_cursos.modules.teacher.useCases;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import javax.security.sasl.AuthenticationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.kazuhiro.gestao_cursos.modules.teacher.TeacherEntity;
import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.AuthTeacherRequestDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
public class AuthTeacherUseCaseTest {
  @Mock
  private TeacherRepository teacherRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthTeacherUseCase authTeacherUseCase;

  @BeforeEach
  void setup() throws Exception {
    Field field = AuthTeacherUseCase.class.getDeclaredField("secretKey");
    field.setAccessible(true);
    field.set(authTeacherUseCase, "test-secret-key");
  }

  @Test
  @DisplayName("Should not authenticate with non existing username.")
  public void shouldNotAuthenticateTeacherWithNonExistingUsername() {
    var loginInfo = AuthTeacherRequestDTO.builder()
        .username("notExistingUser")
        .password("password123")
        .build();

    when(teacherRepository.findByUsername(anyString())).thenReturn(Optional.empty());

    try {
      authTeacherUseCase.execute(loginInfo);
      Assertions.fail("Should trown an exception");
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(UsernameNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not authenticate a teacher with invalid password")
  public void shouldNotAuthenticateTeacherWithInvalidPassword() {
    var loginInfo = AuthTeacherRequestDTO.builder()
        .username("existinguser")
        .password("wrongpassword")
        .build();

    var teacher = TeacherEntity.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678999")
        .password("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .build();

    when(teacherRepository.findByUsername(anyString())).thenReturn(Optional.of(teacher));
    try {
      authTeacherUseCase.execute(loginInfo);
      Assertions.fail("Should Trown Exception");
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(AuthenticationException.class);
    }
  }

  @Test
  @DisplayName("Should authenticate a teacher with valid credentials")
  public void shouldAuthenticateTeacherWithValidCredentials() {
    var loginInfo = AuthTeacherRequestDTO.builder()
        .username("existinguser")
        .password("password123")
        .build();

    var teacher = TeacherEntity.builder()
        .name("Test")
        .username("existinguser")
        .email("teste@teste.com")
        .cpf("12345678909")
        .password("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .id(UUID.randomUUID())
        .build();

    when(teacherRepository.findByUsername(anyString())).thenReturn(Optional.of(teacher));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

    try {
      var authResponse = authTeacherUseCase.execute(loginInfo);
      Assertions.assertThat(authResponse).isNotNull();
      Assertions.assertThat(authResponse.getToken()).isNotBlank();
      Assertions.assertThat(authResponse.getExpiresIn()).isNotNull();
      Assertions.assertThat(authResponse.getRoles()).isNotEmpty();
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail(e.getMessage());
    }
  }
}
