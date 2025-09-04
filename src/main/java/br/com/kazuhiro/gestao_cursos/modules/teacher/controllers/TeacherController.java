package br.com.kazuhiro.gestao_cursos.modules.teacher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kazuhiro.gestao_cursos.modules.teacher.dtos.CreateTeacherDTO;
import br.com.kazuhiro.gestao_cursos.modules.teacher.useCases.CreateTeacherUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Teacher management endpoints")
public class TeacherController {
  @Autowired
  private CreateTeacherUseCase createTeacherUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateTeacherDTO teacherDTO) {
    try {
      createTeacherUseCase.execute(teacherDTO);
      return ResponseEntity.ok().body("Teacher created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
