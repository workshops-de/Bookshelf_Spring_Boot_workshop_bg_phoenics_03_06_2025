package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class BookRepository {
  private final ObjectMapper mapper;
  private final ResourceLoader resourceLoader;

  private List<Book> books;

  BookRepository(ObjectMapper mapper, ResourceLoader resourceLoader) {
    this.mapper = mapper;
    this.resourceLoader = resourceLoader;
  }

  @PostConstruct
  void init() throws IOException {
    final var resource = resourceLoader.getResource("classpath:books.json");
    this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
  }

  List<Book> findAllBooks() {
    return books;
  }

  public Book saveBook(@Valid Book book) {
    books.add(book);
    return book;
  }
}
