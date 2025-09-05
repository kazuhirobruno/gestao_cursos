package br.com.kazuhiro.gestao_cursos.modules.course.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDTO {
  @Schema(description = "Name of the course", example = "Computer Science", required = true)
  private String name;
  @Schema(description = "Number of vacancies available", example = "40", required = true)
  private int slots;
  @Schema(description = "Year and semester of the course", example = "202502", required = true)
  private int semester;
}
