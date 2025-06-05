package de.workshops.bookshelf.book;

import org.springframework.data.repository.ListCrudRepository;

public interface BookJpaRepository extends ListCrudRepository<Book, Long> {
}
