package br.com.kazuhiro.gestao_cursos.modules.teacher.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kazuhiro.gestao_cursos.modules.teacher.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {
  Optional<TeacherEntity> findByUsernameOrCpfOrEmail(String username, String cpf, String email);

  Optional<TeacherEntity> findByUsername(String username);
}
