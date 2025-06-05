package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookJdbcClientRepository.class)
class BookJdbcClientRepositoryTest {
  @Autowired
  private BookJdbcClientRepository repository;

  @Test
  void findAllBooks() {
    final List<Book> books = repository.findAllBooks();
    assertThat(books).hasSize(3);
  }

  @Test
  void saveBook() {
    final Book book = new Book();
    book.setTitle("Title");
    book.setAuthor("Author");
    book.setIsbn("Isbn");
    book.setDescription("Description");
    repository.saveBook(book);

    final List<Book> books = repository.findAllBooks();
    assertThat(books).hasSize(4);
  }
}