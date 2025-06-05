package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(BookRestController.class)
//@Import({BookService.class})
@WithMockUser
class BookRestControllerMocMvcMockedServiceTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private BookService service;

  @Captor
  ArgumentCaptor<String> isbnCaptor;

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAllBooks() throws Exception {
    when(service.getAllBooks()).thenReturn(List.of(new Book(), new Book(), new Book()));
    mockMvc.perform(get("/book"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void getByIsbn() throws Exception {
    when(service.getBookByIsbn(isbnCaptor.capture())).thenReturn(new Book());
    mockMvc.perform(get("/book/2222222"))
        .andDo(print())
        .andExpect(status().isOk());

    var value = isbnCaptor.getValue();
    assertThat(value).isEqualTo("2222222");
  }

  @Test
  void getByAuthor_authorNameTooShort() throws Exception {
    mockMvc.perform(get("/book").param("author", "Ro"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void getAllBooksWithObjectMapper() throws Exception {
    var book1 = new Book();
    book1.setTitle("Design Patterns");

    when(service.getAllBooks()).thenReturn(List.of(new Book(), book1, new Book()));
    var mvcResult = mockMvc.perform(get("/book"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var payload = mvcResult.getResponse().getContentAsString();

    // using type inference
    List<Book> result = objectMapper.readValue(payload, new TypeReference<>() {});
    assertThat(result).hasSize(3)
        .extracting("title")
        .contains("Design Patterns");

    // using array
    var books = objectMapper.readValue(payload, Book[].class);
    assertThat(books).hasSize(3);
  }
}