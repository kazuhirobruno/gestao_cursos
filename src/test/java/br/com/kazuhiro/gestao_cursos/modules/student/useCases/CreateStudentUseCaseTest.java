package br.com.kazuhiro.gestao_cursos.modules.student.useCases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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
import br.com.kazuhiro.gestao_cursos.exceptions.StudentFoundException;
import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.dtos.CreateStudentDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class CreateStudentUseCaseTest {

  @InjectMocks
  private CreateStudentUseCase createStudentUseCase;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Should not create a student with invalid CPF")
  public void shouldNotCreateStudentWithInvalidCPF() {
    var student = CreateStudentDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678999")
        .password("password123")
        .confirmPassword("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .build();
    try {
      createStudentUseCase.execute(student);
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(InvalidCPFException.class);
    }
  }

  @Test
  @DisplayName("Should create a student with valid CPF")
  public void shouldCreateStudentWithValidCPF() throws Exception {
    var student = CreateStudentDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("password123")
        .confirmPassword("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .build();
    Assertions.assertThatCode(() -> createStudentUseCase.execute(student)).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should not be able to create a student with duplicate username, email or CPF")
  public void shouldNotCreateStudentWithDuplicateUsernameEmailOrCPF() {
    var student = CreateStudentDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("password123")
        .confirmPassword("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .build();
    createStudentUseCase.execute(student);
    try {
      when(studentRepository.save(any(StudentEntity.class))).thenReturn(new StudentEntity());
      var createdStudent = CreateStudentDTO.builder()
          .name("Test")
          .username("existinguser")
          .email("test@test.com")
          .cpf("12345678909")
          .password("password123")
          .confirmPassword("password123")
          .birthDate(LocalDate.of(2000, 1, 1))
          .phone("11999999999")
          .build();
      createStudentUseCase.execute(createdStudent);
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(StudentFoundException.class);
    }
  }

  @Test
  @DisplayName("Should encrypt the student's password upon creation")
  public void shouldEncryptPasswordOnCreation() throws Exception {
    var student = CreateStudentDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("password123")
        .confirmPassword("password123")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .build();

    var savedStudent = StudentEntity.builder()
        .id(null)
        .name(student.getName())
        .username(student.getUsername())
        .email(student.getEmail())
        .cpf(student.getCpf())
        .password("encryptedPassword123")
        .birthDate(student.getBirthDate())
        .phone(student.getPhone())
        .build();

    when(studentRepository.save(any(StudentEntity.class))).thenReturn(savedStudent);
    var createdStudent = createStudentUseCase.execute(student);
    Assertions.assertThat(createdStudent.getPassword()).isNotEqualTo("password123");
  }

  @Test
  @DisplayName("Should not create a student when password and confirmPassword do not match")
  public void shouldNotCreateStudentWhenPasswordDoNotMatch() {
    var student = CreateStudentDTO.builder()
        .name("Test")
        .username("existinguser")
        .email("test@test.com")
        .cpf("12345678909")
        .password("password123")
        .confirmPassword("password1234")
        .birthDate(LocalDate.of(2000, 1, 1))
        .phone("11999999999")
        .build();
    try {
      createStudentUseCase.execute(student);
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(PasswordDoNotMatchesException.class);
    }
  }
}
