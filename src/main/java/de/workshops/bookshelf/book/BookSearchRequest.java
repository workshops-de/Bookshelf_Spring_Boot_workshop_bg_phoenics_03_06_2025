package de.workshops.bookshelf.book;

import jakarta.validation.constraints.Size;

record BookSearchRequest(String isbn, @Size(min=3) String author) {
}
