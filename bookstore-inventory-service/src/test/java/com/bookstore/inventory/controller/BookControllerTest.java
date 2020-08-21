package com.bookstore.inventory.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bookstore.inventory.model.Book;
import com.bookstore.inventory.service.BookService;
import com.bookstore.inventory.utils.TestUtility;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
	@MockBean
	private BookService bookService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLogin() throws Exception {

		List<Book> expectedTokenInfo = prepareExpectedResponse();
		final String expectedResponse = TestUtility.mapObjectToJson(expectedTokenInfo);

		Mockito.when(bookService.getAllBooks()).thenReturn(expectedTokenInfo);

		final RequestBuilder builder = MockMvcRequestBuilders.get("/book").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		final MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		final MockHttpServletResponse httpResponse = mvcResult.getResponse();

		final String resultJson = httpResponse.getContentAsString();
		assertThat(resultJson).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), httpResponse.getStatus());
	}

	private List<Book> prepareExpectedResponse() throws JsonProcessingException {
		List<Book> bookList = new ArrayList<>();
		Book javabook = new Book();
		javabook.setName("Java 8");
		javabook.setAuthor("James Gosling");
		javabook.setIsbn("AGGD5678876");
		javabook.setPublisher("O'reilly");
		bookList.add(javabook);
		
		Book phpbook = new Book();
		phpbook.setName("Php 5");
		phpbook.setAuthor("Tommy Joseph");
		phpbook.setIsbn("GHDAGJDJ7889");
		phpbook.setPublisher("O'reilly");
		bookList.add(phpbook);
		return bookList;
	}
}
