package de.workshops.bookshelf;

import de.workshops.bookshelf.book.BookException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BookException.class)
  ProblemDetail handleBooException(BookException ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.I_AM_A_TEAPOT, ex.getMessage());
    problemDetail.setType(URI.create("imateapoterror.html"));
    problemDetail.setTitle("BookException");
    problemDetail.setProperty("category", "Explosion");
    return problemDetail;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ProblemDetail handleConstrainbtViolationException(Exception ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
  }
}
