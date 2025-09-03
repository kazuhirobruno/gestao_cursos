package br.com.kazuhiro.gestao_cursos.modules.student.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDTO {
  private String name;
  private String username;
  private String email;
  private String cpf;
  private String password;
  private String confirmPassword;
  private LocalDate birthDate;
  private String phone;
}
