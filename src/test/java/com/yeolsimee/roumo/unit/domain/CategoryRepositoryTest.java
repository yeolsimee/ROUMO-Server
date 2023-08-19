package com.yeolsimee.roumo.unit.domain;

import com.yeolsimee.roumo.app.category.entity.Category;
import com.yeolsimee.roumo.app.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Category Persistence Test")
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@DisplayName("카테고리를 새로 생성한다.")
	@Test
	void createCategory() {
		// given
		Category category = Category.of("카테고리1");
		categoryRepository.save(category);

		// when
		Category result = categoryRepository.findById(1L).get();

		// then
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getCategoryOrder()).isEqualTo(1L);
	}
}
