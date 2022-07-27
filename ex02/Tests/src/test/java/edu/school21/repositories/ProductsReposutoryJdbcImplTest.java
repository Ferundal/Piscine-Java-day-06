package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ProductsReposutoryJdbcImplTest {
    private DataSource dataSource;
    private ProductsRepository productsRepository;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "Warhammer", 40000),
            new Product(1L, "Warcraft", 3),
            new Product(2L, "Moon", 2024),
            new Product(3L, "Elder Scrolls", 3),
            new Product(4L, "Gothic", 2));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3L, "Elder Scrolls", 3);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "Earth", 2160);
    final Product EXPECTED_SAVE_PRODUCT = new Product(5L, "Postal", 2);
    final Product DELETE_PRODUCT = new Product(4L, "Gothic", 2);
    final List<Product> EXPECTED_AFTER_DELETE_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "Warhammer", 40000),
            new Product(1L, "Warcraft", 3),
            new Product(2L, "Moon", 2024),
            new Product(3L, "Elder Scrolls", 3));

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .generateUniqueName(true)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();
        productsRepository = new ProductsReposutoryJdbcImpl(dataSource);
    }

    @Test
    void testFindAll() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    void testFindById() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(3L).get());
    }

    @Test
    void testUpdate() throws SQLException {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(EXPECTED_UPDATED_PRODUCT.getId()).get());
    }

    @Test
    void testSave() throws SQLException {
        productsRepository.save(new Product(null, "Postal", 2));
        Assertions.assertEquals(EXPECTED_SAVE_PRODUCT, productsRepository.findById(EXPECTED_SAVE_PRODUCT.getId()).get());
    }

    @Test
    void testDelete() throws SQLException {
        productsRepository.delete(DELETE_PRODUCT.getId());
        Assertions.assertEquals(EXPECTED_AFTER_DELETE_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }
}
