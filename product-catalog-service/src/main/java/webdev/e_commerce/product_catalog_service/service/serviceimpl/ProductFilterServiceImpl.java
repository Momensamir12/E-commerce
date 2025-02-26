package webdev.e_commerce.product_catalog_service.service.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import webdev.e_commerce.product_catalog_service.dto.responsedto.ProductResponseDTO;
import webdev.e_commerce.product_catalog_service.model.Product;
import webdev.e_commerce.product_catalog_service.service.ProductFilterService;
import webdev.e_commerce.product_catalog_service.utils.mapstruct.ProductMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductFilterServiceImpl implements ProductFilterService {

    private final MongoTemplate mongoTemplate;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDTO> filterByName(String name, int page, int size) {
        Query query = new Query();
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name can't be null or empty");
        query.addCriteria(Criteria.where("name").regex(".*" + name + ".*", "i"));
        query.with(PageRequest.of(page, size));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products.stream()
                .map(productMapper::productToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> filterByPriceRange(BigDecimal min, BigDecimal max, int page, int size) {
        Query query = new Query();
        if (min == null || max == null)
            throw new IllegalArgumentException("Price ranges can't be null");
        Criteria c = new Criteria().andOperator(Criteria.where("price").gte(min),
                Criteria.where("price").lte(max));
        query.addCriteria(c);
        query.with(PageRequest.of(page, size));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products.stream()
                .map(productMapper::productToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> filterByCategory(String category, int page, int size) {
        Query query = new Query();
        if (category == null || category.isEmpty())
            throw new IllegalArgumentException("Category can't be null or empty");
        query.addCriteria(Criteria.where("category").regex(".*" + category + ".*", "i"));
        query.with(PageRequest.of(page, size)); // Add pagination
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products.stream()
                .map(productMapper::productToProductResponseDTO)
                .collect(Collectors.toList());
    }
}