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
import br.com.kazuhiro.gestao_cursos.exceptions.StudentFoundException;
import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
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
    var student = new StudentEntity();
    student.setName("Test");
    student.setUsername("testuser");
    student.setEmail("test@test.com");
    student.setCpf("12345678900"); // Invalid CPF
    student.setPassword("password123");
    student.setBirthDate(LocalDate.of(2000, 1, 1));
    try {
      createStudentUseCase.execute(student);
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(InvalidCPFException.class);
    }
  }

  @Test
  @DisplayName("Should create a student with valid CPF")
  public void shouldCreateStudentWithValidCPF() throws Exception {
    var student = new StudentEntity();
    student.setName("Test");
    student.setUsername("testuser");
    student.setEmail("test@test.com");
    student.setCpf("12345678909"); // Valid CPF
    student.setPassword("password123");
    student.setBirthDate(LocalDate.of(2000, 1, 1));
    Assertions.assertThatCode(() -> createStudentUseCase.execute(student)).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should not be able to create a student with duplicate username, email or CPF")
  public void shouldNotCreateStudentWithDuplicateUsernameEmailOrCPF() {
    var student = new StudentEntity();
    student.setName("Test");
    student.setUsername("existinguser");
    student.setEmail("test@test.com");
    student.setCpf("12345678909"); // Valid CPF
    student.setPassword("password123");
    student.setBirthDate(LocalDate.of(2000, 1, 1));
    createStudentUseCase.execute(student);
    try {
      createStudentUseCase.execute(student);
    } catch (Exception e) {
      Assertions.assertThat(e).isInstanceOf(StudentFoundException.class);
    }
  }

  @Test
  @DisplayName("Should encrypt the student's password upon creation")
  public void shouldEncryptPasswordOnCreation() throws Exception {
    var student = new StudentEntity();
    student.setName("Test");
    student.setUsername("existinguser");
    student.setEmail("test@test.com");
    student.setCpf("12345678909"); // Valid CPF
    student.setPassword("password123");
    student.setBirthDate(LocalDate.of(2000, 1, 1));
    when(studentRepository.save(any(StudentEntity.class))).thenReturn(new StudentEntity());
    var createdStudent = createStudentUseCase.execute(student);
    Assertions.assertThat(createdStudent.getPassword()).isNotEqualTo("password123");
  }
}
