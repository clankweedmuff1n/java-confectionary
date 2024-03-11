package com.back.confectionary;

import com.back.confectionary.products.Category.Category;
import com.back.confectionary.products.Category.CategoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfectionaryApplicationTests {
	private final JdbcTemplate jdbcTemplate;
	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;
	private final String baseUrl = "http://localhost:8080/api/v1";

	@AfterEach
	void afterEach() {
		String sqlQuery =
				"""
                        delete from _category;
                        delete from _gallery;
                        delete from _products;
                        delete from _reviews;
                        """;
		jdbcTemplate.update(sqlQuery);
	}

	@Test
	@Order(0)
	void shouldCreateCategory() {
		String categoryName = "Empty category";
		String categoryDescription = "Empty category creation";

		CategoryRequest request = CategoryRequest.builder()
				.name(categoryName)
				.description(categoryDescription)
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CategoryRequest> requestHttpEntity = new HttpEntity<>(request, headers);
		Category category = restTemplate.postForObject(baseUrl + "/category/create", /*mapper.writeValueAsString(request)*/requestHttpEntity, Category.class);
		if (category == null) throw new RuntimeException("Category is null");

		assertEquals(1, category.getId(), "Category's id isn't correct");
		assertEquals(categoryName, category.getName(), "Category's name isn't correct");
		assertEquals(categoryDescription, category.getDescription(), "Category's description isn't correct");
		assertNull(category.getGallery	(), "Category's gallery must be null");
		assertNull(category.getPreview(), "Category's preview must be null");
		assertNull(category.getProducts(), "Category's products must be null");
		assertNull(category.getReviews(), "Category's reviews must be null");
	}

	@Test
	@Order(1)
	void getAllCategories() {
		Category[] categories = restTemplate.getForObject(baseUrl + "/category/all", Category[].class);
		if (categories == null) throw new RuntimeException("Category is null");
		assertEquals(0, categories.length);
	}
}
