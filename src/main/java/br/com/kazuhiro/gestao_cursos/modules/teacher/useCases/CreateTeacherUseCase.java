package br.com.kazuhiro.gestao_cursos.modules.teacher.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.kazuhiro.gestao_cursos.exceptions.InvalidCPFException;
import br.com.kazuhiro.gestao_cursos.exceptions.PasswordDoNotMatchesException;
import br.com.kazuhiro.gestao_cursos.exceptions.UserFoundException;
import br.com.kazuhiro.gestao_cursos.modules.teacher.TeacherEntity;
import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.CreateTeacherDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.repository.TeacherRepository;
import br.com.kazuhiro.gestao_cursos.utils.CpfValidator;

@Service
public class CreateTeacherUseCase {
  @Autowired
  private TeacherRepository teacherRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public void execute(CreateTeacherDTO teacherDTO) {
    // check cpf
    if (!CpfValidator.isCPF(teacherDTO.getCpf())) {
      throw new InvalidCPFException("CPF inválido");
    }

    // check passwords matches
    if (!teacherDTO.getPassword().equals(teacherDTO.getConfirmPassword())) {
      throw new PasswordDoNotMatchesException("As senhas não coincidem");
    }

    // check if username, email or cpf already exists
    this.teacherRepository.findByUsernameOrCpfOrEmail(teacherDTO.getUsername(),
        teacherDTO.getCpf(), teacherDTO.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException("Username, e-mail ou CPF já cadastrado.");
        });

    // should encrypt password
    var password = passwordEncoder.encode(teacherDTO.getPassword());
    teacherDTO.setPassword(password);

    var teacher = TeacherEntity.builder()
        .name(teacherDTO.getName())
        .username(teacherDTO.getUsername())
        .email(teacherDTO.getEmail())
        .cpf(teacherDTO.getCpf())
        .password(teacherDTO.getPassword())
        .birthDate(teacherDTO.getBirthDate())
        .phone(teacherDTO.getPhone())
        .build();

    System.out.println("Save teacher");
  }
}
