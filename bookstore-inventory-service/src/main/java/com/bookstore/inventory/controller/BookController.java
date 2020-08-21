package com.bookstore.inventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.inventory.exception.GenericBusinessException;
import com.bookstore.inventory.exception.ReturnCode;
import com.bookstore.inventory.model.Book;
import com.bookstore.inventory.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	// @Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "/book/author/{author}")
	public List<Book> getBookByAuthor(@AuthenticationPrincipal Jwt jwt, @PathVariable String author)
			throws GenericBusinessException {
		List<String> authorities = jwt.getClaimAsStringList("authorities");
		if (authorities.contains("ROLE_ADMIN") || authorities.contains("ROLE_USER")) {
			return bookService.getBooksByAuthorName(author);
		} else {
			throw new GenericBusinessException(ReturnCode.ACCESS_DENIED, "you don't have enough rights.");
		}
	}

	@GetMapping(value = "/book/{name}")
	public List<Book> getBookByName(@AuthenticationPrincipal Jwt jwt, @PathVariable String name)
			throws GenericBusinessException {
		List<String> authorities = jwt.getClaimAsStringList("authorities");
		if (authorities.contains("ROLE_ADMIN") || authorities.contains("ROLE_USER")) {
			return bookService.getBooksByBookName(name);
		} else {
			throw new GenericBusinessException(ReturnCode.ACCESS_DENIED, "you don't have enough rights.");
		}
	}

	@GetMapping(value = "/book")
	public List<Book> getAllBooks(@AuthenticationPrincipal Jwt jwt) throws GenericBusinessException {
		List<String> authorities = jwt.getClaimAsStringList("authorities");
		if (authorities.contains("ROLE_ADMIN") || authorities.contains("ROLE_USER")) {
			return bookService.getAllBooks();
		} else {
			throw new GenericBusinessException(ReturnCode.ACCESS_DENIED, "you don't have enough rights.");
		}
	}

	@PostMapping(value = "/book")
	public Book addBookToInventory(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid Book book)
			throws GenericBusinessException {
		List<String> authorities = jwt.getClaimAsStringList("authorities");

		if (authorities.contains("ROLE_ADMIN")) {
			return bookService.addBookToInventory(book);
		} else {
			throw new GenericBusinessException(ReturnCode.ACCESS_DENIED, "you don't have enough rights.");
		}
	}

	@GetMapping(value = "/book/isbn/{isbn}")
	public Book getBookByIsbn(@AuthenticationPrincipal Jwt jwt, @PathVariable String isbn)
			throws GenericBusinessException {
		List<String> authorities = jwt.getClaimAsStringList("authorities");

		if (authorities.contains("ROLE_ADMIN") || authorities.contains("ROLE_USER")) {
			return bookService.getBookByIsbn(isbn);
		} else {
			throw new GenericBusinessException(ReturnCode.ACCESS_DENIED, "you don't have enough rights.");
		}
	}
}
