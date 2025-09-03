package br.com.kazuhiro.gestao_cursos.modules.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.kazuhiro.gestao_cursos.modules.student.dtos.AuthStudentRequestDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.dtos.AuthStudentResponseDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.useCases.AuthStudentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/student")
@Tag(name = "Auth Student", description = "Student authentication endpoint")
public class AuthStudentController {

  @Autowired
  private AuthStudentUseCase authStudentUseCase;

  @PostMapping("/auth")
  @Operation(summary = "Authenticate a student", description = "Authenticates a student and returns a JWT token.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Authentication successful", content = {
          @Content(schema = @Schema(implementation = AuthStudentResponseDTO.class), mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "401", description = "Invalid credentials", content = {
          @Content(schema = @Schema(example = "Error message"), mediaType = "application/json")
      })
  })
  public ResponseEntity<Object> auth(@RequestBody AuthStudentRequestDTO authStudentRequestDTO) {
    try {
      var token = authStudentUseCase.execute(authStudentRequestDTO);
      return ResponseEntity.ok().body(token);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
