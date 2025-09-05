package br.com.kazuhiro.gestao_cursos.modules.teacher.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Data Transfer Object for student authentication request", example = "{\"username\": \"johndoe\", \"password\": \"password123\"}")
@Builder
public record AuthTeacherRequestDTO(String username, String password) {
}
