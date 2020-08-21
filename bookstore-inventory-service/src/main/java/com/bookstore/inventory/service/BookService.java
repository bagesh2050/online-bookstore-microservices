package com.bookstore.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.inventory.model.Book;
import com.bookstore.inventory.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	public List<Book> getBooksByAuthorName(String authorName) {
		return repository.findByAuthor(authorName);
	}

	public List<Book> getBooksByBookName(String bookName) {
		return repository.findByName(bookName);
	}

	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	public Book addBookToInventory(Book book) {
		return repository.save(book);
	}

	public Book getBookByIsbn(String isbn) {
		return repository.findByIsbn(isbn);
	}
}
