package br.com.kazuhiro.gestao_cursos.modules.course.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kazuhiro.gestao_cursos.modules.course.CourseEntity;
import br.com.kazuhiro.gestao_cursos.modules.course.dtos.CreateCourseDTO;
import br.com.kazuhiro.gestao_cursos.modules.course.repository.CourseRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/teacher/course")
public class CourseController {
  @Autowired
  private CourseRepository courseRepository;

  @PostMapping("/create/")
  @PreAuthorize("hasRole('TEACHER')")
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(HttpServletRequest request, @RequestBody CreateCourseDTO createCourseDTO) {
    try {
      UUID a = UUID.fromString(request.getAttribute("teacher_id").toString());
      CourseEntity courseEntity = CourseEntity.builder()
          .id(UUID.randomUUID())
          .name(createCourseDTO.getName())
          .semester(createCourseDTO.getSemester())
          .slots(createCourseDTO.getSlots())
          .teacherId(a)
          .build();

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseEntity);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
