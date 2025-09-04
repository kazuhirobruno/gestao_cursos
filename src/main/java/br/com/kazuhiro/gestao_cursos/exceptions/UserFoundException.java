package br.com.kazuhiro.gestao_cursos.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException(String message) {
    super("Username, e-mail ou CPF já cadastrado.");
  }
}
