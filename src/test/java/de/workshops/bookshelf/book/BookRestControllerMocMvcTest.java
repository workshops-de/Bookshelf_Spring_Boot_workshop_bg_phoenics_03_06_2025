package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
//uses the H2 database, since it is a scope "test" dependency in pom.xml
@AutoConfigureTestDatabase
class BookRestControllerMocMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getAllBooks() throws Exception {
    mockMvc.perform(get("/book"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
        .andExpect(jsonPath("$[0].title").value("Design Patterns"));
  }

  @Test
  void getByAuthor_authorNameTooShort() throws Exception {
    mockMvc.perform(get("/book").param("author", "Ro"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void getAllBooksWithObjectMapper() throws Exception {
    var mvcResult = mockMvc.perform(get("/book"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var payload = mvcResult.getResponse().getContentAsString();

    // using type inference
    List<Book> result = objectMapper.readValue(payload, new TypeReference<>() {
    });
    assertThat(result).hasSizeGreaterThanOrEqualTo(3)
        .extracting("title")
        .contains("Design Patterns");

    // using array
    var books = objectMapper.readValue(payload, Book[].class);
    assertThat(books).hasSizeGreaterThanOrEqualTo(3);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createBook_roleAdmin() throws Exception {
    var isbn = "1111111";
    var title = "My first book";
    var author = "Birgit Kratz";
    var description = "This is my first book";

    mockMvc.perform(post("/book")
            .content("""
                {
                    "isbn": "%s",
                    "title": "%s",
                    "author": "%s",
                    "description": "%s"
                }""".formatted(isbn, title, author, description))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  void createBook_roleUser_forbidden() throws Exception {
    var isbn = "1111111";
    var title = "My first book";
    var author = "Birgit Kratz";
    var description = "This is my first book";

    mockMvc.perform(post("/book")
            .content("""
                {
                    "isbn": "%s",
                    "title": "%s",
                    "author": "%s",
                    "description": "%s"
                }""".formatted(isbn, title, author, description))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}