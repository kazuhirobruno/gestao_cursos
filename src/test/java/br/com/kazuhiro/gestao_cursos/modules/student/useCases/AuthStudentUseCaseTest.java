package br.com.kazuhiro.gestao_cursos.modules.student.useCases;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import javax.security.sasl.AuthenticationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.lang.reflect.Field;

import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.dtos.AuthStudentRequestDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class AuthStudentUseCaseTest {
  @Mock
  private StudentRepository studentRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthStudentUseCase authStudentUseCase;

  @BeforeEach
  void setup() throws Exception {
    Field field = AuthStudentUseCase.class.getDeclaredField("secretKey");
    field.setAccessible(true);
    field.set(authStudentUseCase, "test-secret-key");
  }

  @Test
  @DisplayName("Should authenticate a student with valid credentials")
  public void shouldNotAuthenticateStudentWithNonExistingUsername() {
    var loginInfo = AuthStudentRequestDTO.builder()
        .username("notExistingUser")
        .password("password123")
        .build();

    when(studentRepository.findByUsername(anyString())).thenReturn(Optional.empty());

    try {
      authStudentUseCase.execute(loginInfo);
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(UsernameNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not authenticate a student with invalid password")
  public void shouldNotAuthenticateStudentWithInvalidPassword() {
    var loginInfo = AuthStudentRequestDTO.builder()
        .username("existinguser")
        .password("wrongpassword")
        .build();

    var student = StudentEntity.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678999")
        .password("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .build();

    when(studentRepository.findByUsername(anyString())).thenReturn(Optional.of(student));
    try {
      authStudentUseCase.execute(loginInfo);
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(AuthenticationException.class);
    }
  }

  @Test
  @DisplayName("Should authenticate a student with valid credentials")
  public void shouldAuthenticateStudentWithValidCredentials() {
    var loginInfo = AuthStudentRequestDTO.builder()
        .username("existinguser")
        .password("password123")
        .build();

    var student = StudentEntity.builder()
        .name("Test")
        .username("existinguser")
        .email("teste@teste.com")
        .cpf("12345678909")
        .password("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .id(UUID.randomUUID())
        .build();

    when(studentRepository.findByUsername(anyString())).thenReturn(Optional.of(student));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

    try {
      var authResponse = authStudentUseCase.execute(loginInfo);
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
