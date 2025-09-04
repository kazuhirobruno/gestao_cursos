package br.com.kazuhiro.gestao_cursos.modules.teacher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kazuhiro.gestao_cursos.modules.teacher.TeacherEntity;
import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.CreateTeacherDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.useCases.CreateTeacherUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Teacher management endpoints")
public class TeacherController {
  @Autowired
  private CreateTeacherUseCase createTeacherUseCase;

  @PostMapping("/")
  @Operation(summary = "Create a new teacher", description = "Creates a new teacher with the provided details.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Teacher created succesfully", content = {
          @Content(schema = @Schema(implementation = TeacherEntity.class), mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "Invalid input or teacher already existis", content = {
          @Content(schema = @Schema(example = "Error message"), mediaType = "application/json")
      })
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CreateTeacherDTO teacherDTO) {
    try {
      var result = createTeacherUseCase.execute(teacherDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
