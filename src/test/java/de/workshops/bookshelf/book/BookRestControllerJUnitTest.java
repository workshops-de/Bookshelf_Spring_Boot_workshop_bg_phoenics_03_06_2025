package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRestControllerJUnitTest {
  @InjectMocks
  private BookRestController controller;
  @Mock
  private BookService service;


  @Test
  void getAllBooks() {
    when(service.getAllBooks()).thenReturn(List.of(new Book(), new Book(), new Book()));

    assertEquals(3, controller.getAllBooks().size());
    assertThat(controller.getAllBooks()).hasSize(3);
  }
}