package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

public class ProductFactory {

    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = new Product(1L, "Console PlayStation 5", "Lorem ipsum dolor sit amet", 3999.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/7-big.jpg");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name) {
        Product product = createProduct();
        product.getName();
        return product;
    }
}
