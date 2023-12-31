package io.github.emmaea.skywalker.util;

import io.github.emmaea.skywalker.model.*;
import io.github.emmaea.skywalker.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
public class ShopSeedRunner implements CommandLineRunner {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public void run(String... args) throws Exception {

        Shop shop = new Shop();
        shop.setName("Teen paradise");
        shop.setRating(4.5);

        Product product = new Product();
        product.setShop(shop);
        product.setName("Nike Sneaker");
        product.setRating(4.0);
        product.setPrice(350);
        product.setCurrency("GHS");

        shop.setProducts(Collections.singleton(product));

        Business business = new Business();
        business.setEmployeesSize(10);
        business.setType(BusinessType.SOLE_PROPRIETOR);
        business.setShop(shop);

        Location location = new Location();
        location.setName("Achimota");
        location.setShop(shop);
        location.setLongitude(1.0011);
        location.setLatitude(0.12221);

        Vendor vendor = new Vendor();
        vendor.setPhone("241231234");
        vendor.setCountryCode("+233");
        vendor.setFirstName("John");
        vendor.setLastName("Doe");
        vendor.setEmail("johndoe@gmail.com");
        vendor.setLocation(location);
        vendor.setOwnedShops(Collections.singleton(shop));

        location.setVendor(vendor);
        shop.setLocations(Collections.singleton(location));
        shop.setVendor(vendor);
        shop.setBusinessType(business);

        Shop shop1 = new Shop();
        shop1.setName("Tech World");
        shop1.setRating(3.0);

        Product product1 = new Product();
        product1.setShop(shop1);
        product1.setName("Macbook Pro");
        product1.setDetail("Brand new M2 16GB 512 SSD 14 inches");
        product1.setRating(5.0);
        product1.setPrice(21350);
        product1.setCurrency("GHS");

        shop1.setProducts(Collections.singleton(product1));

        Business business1 = new Business();
        business1.setEmployeesSize(5);
        business1.setType(BusinessType.FRANCHISE);
        business1.setShop(shop1);

        Location location1 = new Location();
        location1.setName("Mile 7");
        location1.setShop(shop1);
        location1.setLongitude(5.0011);
        location1.setLatitude(9.12221);

        Vendor vendor1 = new Vendor();
        vendor1.setPhone("241231235");
        vendor1.setCountryCode("+234");
        vendor1.setFirstName("Mark");
        vendor1.setLastName("Will");
        vendor1.setEmail("markwill@gmail.com");
        vendor1.setLocation(location1);
        vendor1.setOwnedShops(Collections.singleton(shop1));

        location1.setVendor(vendor1);
        shop1.setLocations(Collections.singleton(location1));
        shop1.setVendor(vendor1);
        shop1.setBusinessType(business1);

        shopRepository.save(shop);
        shopRepository.save(shop1);
    }
}
