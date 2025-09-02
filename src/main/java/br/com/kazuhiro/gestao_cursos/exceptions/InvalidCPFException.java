package br.com.kazuhiro.gestao_cursos.exceptions;

public class InvalidCPFException extends RuntimeException {
  public InvalidCPFException(String message) {
    super("CPF inválido.");
  }
}
