package br.com.kazuhiro.gestao_cursos.modules.student.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthStudentResponseDTO {
  @Schema(description = "JWT token for authenticated student", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String token;
  @Schema(description = "Expiration time of the token in milliseconds", example = "3600000")
  private Long expiresIn;
  @Schema(description = "Roles assigned to the student", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
  private List<String> roles;
}
