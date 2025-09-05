package br.com.kazuhiro.gestao_cursos.modules.course.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kazuhiro.gestao_cursos.modules.course.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

}
