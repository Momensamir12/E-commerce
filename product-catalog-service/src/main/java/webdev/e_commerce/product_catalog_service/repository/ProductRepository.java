package webdev.e_commerce.product_catalog_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import webdev.e_commerce.product_catalog_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findAll(Pageable pageable);

}
