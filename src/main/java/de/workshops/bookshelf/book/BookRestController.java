package de.workshops.bookshelf.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("book")
@Validated
class BookRestController {
  private final BookService service;

  BookRestController(BookService service) {
    this.service = service;
  }

  @GetMapping
  List<Book> getAllBooks() {
    return service.getAllBooks();
  }

  @GetMapping("/{isbn}")
  Book getBookByIsbn(@PathVariable(name = "isbn") String anIsbn) {
    return service.getBookByIsbn(anIsbn);
  }

  @GetMapping(params = "author")
  ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam(name = "author", required = false) @Size(min = 3) String anAuthor) {
    var booksByAuthor = service.getBooksByAuthor(anAuthor);
    if (booksByAuthor.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(booksByAuthor);
  }

  @PostMapping("search")
  List<Book> searchBook(@RequestBody @Valid BookSearchRequest searchRequest) {
    return service.searchBook(searchRequest);
  }

  @PostMapping
  ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
    return ResponseEntity.created(URI.create("book/"+ book.getIsbn())).body(service.createBook(book));
  }

  @ExceptionHandler(BookException.class)
  ResponseEntity<String> handleBookException(BookException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
