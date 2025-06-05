package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class BookJdbcRepository {
  private final JdbcTemplate jdbcTemplate;

  BookJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  List<Book> findAllBooks() {
    var query = "SELECT * FROM BOOK";
    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Book.class));
  }

  Book saveBook(Book book) {
    var query = "INSERT INTO book (title, description, author, isbn) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(query, book.getTitle(), book.getDescription(), book.getAuthor(), book.getIsbn());
    return book;
  }
}
