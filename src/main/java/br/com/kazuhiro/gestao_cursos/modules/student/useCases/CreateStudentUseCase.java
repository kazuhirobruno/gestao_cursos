package br.com.kazuhiro.gestao_cursos.modules.student.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.repository.StudentRepository;

@Service
public class CreateStudentUseCase {

  @Autowired
  private StudentRepository studentRepository;

  public StudentEntity execute(StudentEntity student) {
    // check if username, email or cpf already exists

    // should encrypt password
    return studentRepository.save(student);
  }
}
