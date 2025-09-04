package br.com.kazuhiro.gestao_cursos.modules.student;

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
@Entity(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
  @Id
  @GeneratedValue
  private UUID id;

  @NotBlank(message = "Informe o seu nome")
  @Schema(example = "John Doe")
  private String name;

  @NotNull(message = "Informe a data de nascimento")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(example = "2000-01-01")
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
  @Schema(example = "AyP{<q$qxT=W$]uU0V1!:AI<c}gS4heYe%m9uE6c>F)ybEpC{P',iP?D")
  private String password;

  @NotBlank(message = "O telefone é obrigatório")
  @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter apenas números e ter 10 ou 11 dígitos")
  @Schema(example = "11999999999")
  private String phone;

  @CreationTimestamp
  @Schema(example = "2023-10-05T14:48:00.000Z")
  private LocalDateTime createdAt;
}