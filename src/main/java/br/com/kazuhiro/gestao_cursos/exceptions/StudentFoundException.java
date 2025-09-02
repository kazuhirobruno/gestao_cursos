package br.com.kazuhiro.gestao_cursos.exceptions;

public class StudentFoundException extends RuntimeException {
  public StudentFoundException(String message) {
    super("Username, e-mail ou CPF já cadastrado.");
  }
}
