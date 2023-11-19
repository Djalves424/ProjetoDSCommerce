package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
