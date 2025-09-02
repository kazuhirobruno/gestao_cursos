package br.com.kazuhiro.gestao_cursos.modules.student.useCases;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.kazuhiro.gestao_cursos.exceptions.InvalidCPFException;
import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class CreateStudentUseCaseTest {

  @InjectMocks
  private CreateStudentUseCase createStudentUseCase;

  @Mock
  private StudentRepository studentRepository;

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
}
