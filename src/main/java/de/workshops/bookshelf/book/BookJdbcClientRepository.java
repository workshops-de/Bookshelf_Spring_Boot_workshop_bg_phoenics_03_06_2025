package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class BookJdbcClientRepository {
  private final JdbcClient jdbcClient;

  BookJdbcClientRepository(JdbcClient jdbcClient1) {
    this.jdbcClient = jdbcClient1;
  }

  List<Book> findAllBooks() {
    var sql = "SELECT * FROM BOOK";
    return jdbcClient.sql(sql)
        .query(new BeanPropertyRowMapper<>(Book.class))
        .list();
  }

  Book saveBook(Book book) {
    var sql = "INSERT INTO book (title, description, author, isbn) VALUES (:title, :description, :author, :isbn)";
    jdbcClient.sql(sql)
        .param("title", book.getTitle())
        .param("description", book.getDescription())
        .param("author", book.getAuthor())
        .param("isbn", book.getIsbn())
        .update();
    return book;
  }

}
