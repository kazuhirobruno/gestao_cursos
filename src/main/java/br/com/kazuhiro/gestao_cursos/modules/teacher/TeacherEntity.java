package br.com.kazuhiro.gestao_cursos.modules.teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "teacher")
public class TeacherEntity {
  @Id
  @GeneratedValue
  private UUID id;

  @Schema(example = "John Doe")
  @NotBlank(message = "Informe o seu nome")
  private String name;

  @Schema(example = "2000-01-01T00:00:00")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @NotNull(message = "Informe a data de nascimento")
  private LocalDate birthDate;

  @NotBlank(message = "CPF é obrigatório")
  @Schema(example = "12345678909")
  private String cpf;

  @NotBlank(message = "O campo username é obrigatório")
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
  @Schema(example = "johndoe")
  private String username;

  @Email(message = "O campo [email] deve conter um e-mail válido")
  @Schema(example = "johndoe@test.com")
  private String email;

  @NotBlank(message = "Preencha sua senha")
  @Length(min = 8, max = 128, message = "A senha deve conter entre 8 e 128 caracteres")
  @Schema(example = "0123456789")
  private String password;

  @NotBlank(message = "Preencha seu telefone")
  @Length(min = 10, max = 11, message = "O telefone deve conter entre 10 e 11 caracteres")
  @Schema(example = "11999999999")
  private String phone;

  @CreationTimestamp
  @Schema(example = "2023-01-01T00:00:00")
  private LocalDateTime createdAt;
}
