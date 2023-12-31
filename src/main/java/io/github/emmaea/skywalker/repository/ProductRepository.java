package io.github.emmaea.skywalker.repository;

import io.github.emmaea.skywalker.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAllByShopId(long shopId);
}
