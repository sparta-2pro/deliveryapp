package com.twopro.deliveryapp.common.config.generator;

import com.twopro.deliveryapp.store.entity.Category;
import com.twopro.deliveryapp.store.repository.CategoryRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InitDataGenerator {

    @Bean
    public ApplicationRunner initData(CategoryRepository categoryRepository) {
        return args -> {
            categoryRepository.save(Category.builder().name("중식").build());
            categoryRepository.save(Category.builder().name("한식").build());
            categoryRepository.save(Category.builder().name("일식").build());
            categoryRepository.save(Category.builder().name("카페").build());
        };
    }
}
