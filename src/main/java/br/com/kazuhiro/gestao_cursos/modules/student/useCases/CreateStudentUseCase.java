package br.com.kazuhiro.gestao_cursos.modules.student.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.kazuhiro.gestao_cursos.exceptions.InvalidCPFException;
import br.com.kazuhiro.gestao_cursos.exceptions.StudentFoundException;
import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.repository.StudentRepository;
import br.com.kazuhiro.gestao_cursos.utils.CpfValidator;

@Service
public class CreateStudentUseCase {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public StudentEntity execute(StudentEntity student) {
    // check cpf
    if (!CpfValidator.isCPF(student.getCpf())) {
      throw new InvalidCPFException("CPF inválido");
    }

    // check if username, email or cpf already exists
    this.studentRepository.findByUsernameOrCpfOrEmail(student.getUsername(),
        student.getCpf(), student.getEmail())
        .ifPresent(user -> {
          throw new StudentFoundException("Username, e-mail ou CPF já cadastrado.");
        });

    // should encrypt password
    var password = passwordEncoder.encode(student.getPassword());
    student.setPassword(password);

    var regisgeredStudent = studentRepository.save(student);
    return regisgeredStudent;
  }
}
