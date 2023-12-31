package io.github.emmaea.skywalker.controller;

import io.github.emmaea.skywalker.model.Product;
import io.github.emmaea.skywalker.model.Shop;
import io.github.emmaea.skywalker.model.response.ApiResponse;
import io.github.emmaea.skywalker.repository.ProductRepository;
import io.github.emmaea.skywalker.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/skywalker/")
@AllArgsConstructor
public class ShopController {

    private ShopRepository shopRepository;
    private ProductRepository productRepository;

    @GetMapping("shops")
    public ResponseEntity<ApiResponse<Set<Shop>>> getAllShops(
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "size", required = false, defaultValue = "10") int pageSize) {

        Pageable pageShops = PageRequest.of(pageNo, pageSize).withSort(Sort.by("name"));

        ApiResponse<Set<Shop>> shops = new ApiResponse<>(
                "SUCCESS",
                "ALL SHOPS",
                shopRepository.findAll(pageShops).toSet(),
                null
        );

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping("shops/{id}")
    public ResponseEntity<ApiResponse<Shop>> getShop(@PathVariable(name = "id") long shopId) {

        Optional<Shop> ret = shopRepository.findById(shopId);
        ApiResponse<Shop> shop = new ApiResponse<>();

        if (ret.isPresent()) {
            shop.setData(ret.get());
            shop.setStatus("SUCCESS");
            shop.setMessage("SHOP FOUND");
            return new ResponseEntity<>(shop, HttpStatus.OK);
        }

        shop.setStatus("FAILED");
        shop.setMessage("SHOP NOT FOUND");
        return new ResponseEntity<>(shop, HttpStatus.NOT_FOUND);
    }

    @GetMapping("shops/{id}/products")
    public ResponseEntity<ApiResponse<Set<Product>>> getProductsByShopId(@PathVariable(name = "id") long shopId) {

        Optional<Shop> ret = shopRepository.findById(shopId);
        ApiResponse<Set<Product>> products = new ApiResponse<>();

        if (ret.isPresent()) {
            products.setData(ret.get().getProducts());
            products.setStatus("SUCCESS");
            products.setMessage("PRODUCTS FOUND FOR SHOP");
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

        products.setStatus("FAILED");
        products.setMessage("NO PRODUCTS FOUND FOR SHOP ID SPECIFIED");
        return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
    }

    @GetMapping("shops/products")
    public ResponseEntity<ApiResponse<Set<Product>>> getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "size", required = false, defaultValue = "10") int pageSize) {

        Pageable pageProducts= PageRequest.of(pageNo, pageSize).withSort(Sort.by("name"));

        ApiResponse<Set<Product>> products = new ApiResponse<>(
                "SUCCESS",
                "ALL PRODUCTS",
                productRepository.findAll(pageProducts).toSet(),
                null
        );

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
