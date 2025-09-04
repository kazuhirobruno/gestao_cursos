package br.com.kazuhiro.gestao_cursos.modules.teacher.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherDTO {
  @Schema(description = "Full name of the teacher", example = "John Doe", required = true)
  private String name;
  @Schema(description = "Birth date of the teacher in YYYY-MM-DD format", example = "2000-01-01", required = true)
  private LocalDate birthDate;
  @Schema(description = "Username for the teacher", example = "johndoe", required = true)
  private String username;
  @Schema(description = "Email address of the teacher", example = "johndoe@test.com")
  private String email;
  @Schema(description = "CPF number of the teacher", example = "12345678909", required = true)
  private String cpf;
  @Schema(description = "Password for the teacher account", example = "password123", required = true)
  private String password;
  @Schema(description = "Confirmation of the password", example = "password123", required = true)
  private String confirmPassword;
  @Schema(description = "Phone number of the teacher", example = "11999999999", required = true)
  private String phone;
}
