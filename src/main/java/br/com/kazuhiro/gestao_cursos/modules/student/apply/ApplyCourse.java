package br.com.kazuhiro.gestao_cursos.modules.student.apply;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.kazuhiro.gestao_cursos.modules.course.CourseEntity;
import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;

@Entity(name = "apply_course")
public class ApplyCourse {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "student_id", referencedColumnName = "id")
  private StudentEntity student;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "course_id", referencedColumnName = "id")
  private CourseEntity courseEntity;
}
