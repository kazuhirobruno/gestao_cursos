package br.com.kazuhiro.gestao_cursos.modules.student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "student")
public class StudentEntity {
  @Id
  @GeneratedValue
  private UUID id;

  @NotBlank(message = "Informe o seu nome")
  private String name;

  @NotNull(message = "Informe a data de nascimento")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @NotBlank(message = "CPF é obrigatório")
  private String cpf;

  @NotBlank(message = "O campo username é obrigatório")
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
  private String username;

  @Email(message = "O campo [email] deve conter um e-mail válido")
  private String email;

  @NotBlank(message = "Preencha sua senha")
  @Length(min = 10, max = 100, message = "A senha deve conter entre 8 e 128 caracteres")
  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
