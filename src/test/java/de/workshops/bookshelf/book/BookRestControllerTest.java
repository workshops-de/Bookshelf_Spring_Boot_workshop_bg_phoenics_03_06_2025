package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRestControllerTest {

  @Autowired
  private BookRestController controller;

  @Test
  void getAllBooks() {
    assertEquals(3, controller.getAllBooks().size());
    assertThat(controller.getAllBooks()).hasSize(3);
  }
}