package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class BookJpaRepositoryTest {

  @Autowired
  private  BookJpaRepository bookJpaRepository;

  @Test
  void findAll() {
    var all = bookJpaRepository.findAll();
    assertNotNull(all);
  }
}