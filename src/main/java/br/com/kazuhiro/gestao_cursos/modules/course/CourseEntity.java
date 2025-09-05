package br.com.kazuhiro.gestao_cursos.modules.course;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.kazuhiro.gestao_cursos.modules.teacher.TeacherEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "course")
public class CourseEntity {
  @Id
  @GeneratedValue
  private UUID id;

  @NotBlank(message = "Informe o nome do curso")
  @Schema(example = "Computer science")
  private String name;

  @ManyToOne
  @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
  private TeacherEntity teacherEntity; // Foreign key to Teacher entity

  @NotNull(message = "Informe o número de vagas")
  @Schema(example = "5")
  private int slots;

  @NotNull(message = "Informe o ano e o semestre - YYYYSemester")
  @Schema(example = "202502")
  private int semester;

  @CreationTimestamp
  @Schema(example = "2023-10-05T14:48:00.000Z")
  private LocalDateTime createdAt;

  @Schema(example = "b6c44857-cff8-4599-8b83-f654e871d03e")
  @NotNull(message = "Informe o ID do professor")
  @Column(name = "teacher_id")
  private UUID teacherId;

}
