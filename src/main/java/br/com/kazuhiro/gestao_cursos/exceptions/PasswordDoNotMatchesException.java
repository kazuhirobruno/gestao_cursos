package br.com.kazuhiro.gestao_cursos.exceptions;

public class PasswordDoNotMatchesException extends RuntimeException {
  public PasswordDoNotMatchesException(String message) {
    super("As senhas não coincidem");
  }

}
