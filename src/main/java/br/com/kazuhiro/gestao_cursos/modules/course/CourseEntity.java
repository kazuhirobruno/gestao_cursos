package br.com.kazuhiro.gestao_cursos.modules.course;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.kazuhiro.gestao_cursos.modules.teacher.TeacherEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "course")
public class CourseEntity {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String name;

  @OneToOne
  @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
  private TeacherEntity teacherEntity; // Foreign key to Teacher entity

  @Column(nullable = false)
  private int slots;

  @Column(nullable = false)
  private int occupiedSlots;

  @Column(nullable = false)
  private int year;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
