package br.com.kazuhiro.gestao_cursos.modules.student.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kazuhiro.gestao_cursos.modules.student.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
  Optional<StudentEntity> findByUsernameOrCpfOrEmail(String username, String cpf, String email);
}
