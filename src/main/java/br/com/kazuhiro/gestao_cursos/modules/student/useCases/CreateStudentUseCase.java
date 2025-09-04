package br.com.kazuhiro.gestao_cursos.modules.student.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.kazuhiro.gestao_cursos.exceptions.InvalidCPFException;
import br.com.kazuhiro.gestao_cursos.exceptions.PasswordDoNotMatchesException;
import br.com.kazuhiro.gestao_cursos.exceptions.UserFoundException;
import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.dtos.CreateStudentDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.repository.StudentRepository;
import br.com.kazuhiro.gestao_cursos.utils.CpfValidator;

@Service
public class CreateStudentUseCase {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public StudentEntity execute(CreateStudentDTO studentDTO) {
    // check cpf
    if (!CpfValidator.isCPF(studentDTO.getCpf())) {
      throw new InvalidCPFException("CPF inválido");
    }

    // check if password matches
    if (!studentDTO.getPassword().equals(studentDTO.getConfirmPassword())) {
      throw new PasswordDoNotMatchesException("As senhas não coincidem");
    }

    // check if username, email or cpf already exists
    this.studentRepository.findByUsernameOrCpfOrEmail(studentDTO.getUsername(),
        studentDTO.getCpf(), studentDTO.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException("Username, e-mail ou CPF já cadastrado.");
        });

    // should encrypt password
    var password = passwordEncoder.encode(studentDTO.getPassword());
    studentDTO.setPassword(password);

    var student = StudentEntity.builder()
        .name(studentDTO.getName())
        .username(studentDTO.getUsername())
        .email(studentDTO.getEmail())
        .cpf(studentDTO.getCpf())
        .password(studentDTO.getPassword())
        .birthDate(studentDTO.getBirthDate())
        .phone(studentDTO.getPhone())
        .build();
    var regisgeredStudent = studentRepository.save(student);
    return regisgeredStudent;
  }
}
