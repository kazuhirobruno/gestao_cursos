package br.com.kazuhiro.gestao_cursos.modules.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.dtos.CreateStudentDTO;
import br.com.kazuhiro.gestao_cursos.modules.student.useCases.CreateStudentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Student management endpoints")
public class StudentController {
  @Autowired
  private CreateStudentUseCase createStudentUseCase;

  @PostMapping("/")
  @Operation(summary = "Create a new student", description = "Creates a new student with the provided details.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Student created successfully", content = {
          @Content(schema = @Schema(implementation = StudentEntity.class), mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "Invalid input or student already exists", content = {
          @Content(schema = @Schema(example = "Error message"), mediaType = "application/json")
      })
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CreateStudentDTO studentDTO) {
    try {
      var result = createStudentUseCase.execute(studentDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
