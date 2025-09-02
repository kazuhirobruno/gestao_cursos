package br.com.kazuhiro.gestao_cursos.modules.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.useCases.CreateStudentUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {
  @Autowired
  private CreateStudentUseCase createStudentUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody StudentEntity student) {
    // System.out.println(student.getName());
    try {
      var result = createStudentUseCase.execute(student);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
