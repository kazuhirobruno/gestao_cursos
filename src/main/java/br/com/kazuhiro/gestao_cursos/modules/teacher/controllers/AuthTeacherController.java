package br.com.kazuhiro.gestao_cursos.modules.teacher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.AuthTeacherRequestDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.AuthTeacherResponseDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.useCases.AuthTeacherUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Auth Teacher", description = "Teacher authentication endpoint")
public class AuthTeacherController {
  @Autowired
  private AuthTeacherUseCase authTeacherUseCase;

  @PostMapping("/auth")
  @Operation(summary = "Authenticate a teacher", description = "Authenticates a teacher and returns JWT token.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Authentication successful", content = {
          @Content(schema = @Schema(implementation = AuthTeacherResponseDTO.class), mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "401", description = "Invalid credentials", content = {
          @Content(schema = @Schema(example = "Error message"), mediaType = "application/json")
      })
  })
  public ResponseEntity<Object> auth(@RequestBody AuthTeacherRequestDTO authTeacherRequestDTO) {
    try {
      var token = authTeacherUseCase.execute(authTeacherRequestDTO);
      return ResponseEntity.ok().body(token);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
