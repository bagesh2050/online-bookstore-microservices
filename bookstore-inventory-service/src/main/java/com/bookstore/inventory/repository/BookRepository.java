package com.bookstore.inventory.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.inventory.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
	List<Book> findByAuthor(String author);
	
	List<Book> findByName(String name);
	
	Book findByIsbn(String isbn);
}
