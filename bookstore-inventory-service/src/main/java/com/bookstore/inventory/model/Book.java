package com.bookstore.inventory.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class Book {
	private String name;
	private String isbn;
	private String author;
	private String publisher;
}
