package de.workshops.bookshelf.book;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
  private final BookJdbcRepository repository;

  public BookService(BookJdbcRepository repository) {
    this.repository = repository;
  }

  List<Book> getAllBooks() {
    return repository.findAllBooks();
  }

  Book getBookByIsbn(String anIsbn) {
    return repository.findAllBooks().stream()
        .filter(book -> book.getIsbn().equals(anIsbn))
        .findFirst()
        .orElseThrow(() -> new BookException("No book for this isbn"));
  }

  List<Book> getBooksByAuthor(String anAuthor) {
    return repository.findAllBooks().stream()
        .filter(book -> book.getAuthor().contains(anAuthor))
        .toList();
  }

  List<Book> searchBook(BookSearchRequest searchRequest) {
    return repository.findAllBooks().stream()
        .filter(book -> book.getIsbn().equals(searchRequest.isbn()) || book.getAuthor().contains(searchRequest.author()))
        .toList();
  }

  public Book createBook(@Valid Book book) {
    return repository.saveBook(book);
  }
}
