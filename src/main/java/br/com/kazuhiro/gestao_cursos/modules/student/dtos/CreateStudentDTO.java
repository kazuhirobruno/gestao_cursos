package br.com.kazuhiro.gestao_cursos.modules.student.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDTO {
  @Schema(description = "Full name of the student", example = "John Doe", required = true)
  private String name;
  @Schema(description = "Username for the student", example = "johndoe", required = true)
  private String username;
  @Schema(description = "Email address of the student", example = "johndoe@test.com", required = true)
  private String email;
  @Schema(description = "CPF number of the student", example = "12345678909", required = true)
  private String cpf;
  @Schema(description = "Password for the student account", example = "password123", required = true)
  private String password;
  @Schema(description = "Confirmation of the password", example = "password123", required = true)
  private String confirmPassword;
  @Schema(description = "Birth date of the student in YYYY-MM-DD format", example = "2000-01-01", required = true)
  private LocalDate birthDate;
  @Schema(description = "Phone number of the student", example = "11999999999", required = true)
  private String phone;
}
