package br.com.kazuhiro.gestao_cursos.modules.teacher.useCases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.kazuhiro.gestao_cursos.exceptions.InvalidCPFException;
import br.com.kazuhiro.gestao_cursos.exceptions.PasswordDoNotMatchesException;
import br.com.kazuhiro.gestao_cursos.exceptions.UserFoundException;
import br.com.kazuhiro.gestao_cursos.modules.teacher.TeacherEntity;
import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.CreateTeacherDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
public class CreateTeacherUseCaseTest {
  @InjectMocks
  private CreateTeacherUseCase createTeacherUseCase;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private TeacherRepository teacherRepository;

  @Test
  @DisplayName("Should not create a teacher with invalid CPF")
  public void shouldNotCreateTeacherWithInvalidCPF() {
    var teacher = CreateTeacherDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678900")
        .password("password123")
        .confirmPassword("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("1234567890")
        .build();

    try {
      createTeacherUseCase.execute(teacher);
      Assertions.fail("Expected InvalidCPFException to be thrown");
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(InvalidCPFException.class);
    }
  }

  @Test
  @DisplayName("Should create a teacher when cpf is valid")
  public void shouldCreateTeacherWhenCPFIsValid() {
    var teacher = CreateTeacherDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("password123")
        .confirmPassword("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("1234567890")
        .build();

    try {
      createTeacherUseCase.execute(teacher);
    } catch (Exception e) {
      Assertions.fail("Did not expect an exception to be thrown");
    }
  }

  @Test
  @DisplayName("Should not create a teacher when passwords do not match")
  public void shouldNotCreateTeacherWhenPasswordsDoNotMatch() {
    var teacher = CreateTeacherDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("password123")
        .confirmPassword("password1234")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("1234567890")
        .build();

    try {
      createTeacherUseCase.execute(teacher);
      Assertions.fail("Expected PasswordDoNotMatchesException to be thrown");
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(PasswordDoNotMatchesException.class);
    }
  }

  @Test
  @DisplayName("Should Encrypt password")
  public void shouldEncrpytPasswordOnCreation() throws Exception {
    var teacher = CreateTeacherDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("password123")
        .confirmPassword("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("1234567890")
        .build();

    var savedTeacher = TeacherEntity.builder()
        .id(UUID.randomUUID())
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("encryptedPassword")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("1234567890")
        .build();

    when(teacherRepository.save(any(TeacherEntity.class))).thenReturn(savedTeacher);
    var createdTeacher = createTeacherUseCase.execute(teacher);
    Assertions.assertThat(createdTeacher.getPassword()).isNotEqualTo("password123");
  }

  @Test
  @DisplayName("Should not create teacher with existing username, email or cpf")
  public void shouldNotCreateTeacherWithExistingUsernameEmailOrCPF() {
    var savedTeacher = TeacherEntity.builder()
        .id(UUID.randomUUID())
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("encryptedPassword")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("1234567890")
        .build();
    when(teacherRepository.findByUsernameOrCpfOrEmail(anyString(), anyString(), anyString()))
        .thenReturn(Optional.of(savedTeacher));

    try {
      var teacher = CreateTeacherDTO.builder()
          .name("Test")
          .username("existinguser")
          .email("test@test.com")
          .cpf("12345678909")
          .password("password123")
          .confirmPassword("password123")
          .birthDate(LocalDate.of(2000, 1, 1))
          .phone("1234567890")
          .build();

      createTeacherUseCase.execute(teacher);
      Assertions.fail("Should not create new user with existing CPF, email or username");
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(UserFoundException.class);
    }
  }
}
