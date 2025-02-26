package webdev.e_commerce.product_catalog_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webdev.e_commerce.common.APIResponse;
import webdev.e_commerce.product_catalog_service.service.ProductFilterService;
import webdev.e_commerce.product_catalog_service.service.ProductService;

import java.math.BigDecimal;

import static webdev.e_commerce.product_catalog_service.utils.api.APIHelperUtil.createUnifiedResponse;
import static webdev.e_commerce.product_catalog_service.utils.api.APIURI.userURI;
@RestController
@RequestMapping(userURI)
@AllArgsConstructor
@Tag(name = "Product catalog service user Controller", description = "APIs related to user operations in product catalog service")
public class ProductUserController {

    private final ProductService productService;
    private final ProductFilterService productFilterService;

    @Operation(summary = "Find a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/id")
    public ResponseEntity<APIResponse> findById(@RequestParam("id") String id) {
        return createUnifiedResponse(productService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Find products by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    @GetMapping("/search")
    public ResponseEntity<APIResponse> findAllByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return createUnifiedResponse(productFilterService.filterByName(name, page, size), HttpStatus.OK);
    }

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    @GetMapping
    public ResponseEntity<APIResponse> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return createUnifiedResponse(productService.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<APIResponse> findAllSorted(
            @RequestParam(value = "sort", defaultValue = "name") String sortField,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return createUnifiedResponse(productService.findAllSorted(page, size, sortField), HttpStatus.OK);
    }

    @Operation(summary = "Find products by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    @GetMapping("/category")
    public ResponseEntity<APIResponse> findAllByCategory(
            @RequestParam(value = "category") String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return createUnifiedResponse(productFilterService.filterByCategory(category, page, size), HttpStatus.OK);
    }

    @Operation(summary = "Find products by price range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    @GetMapping("/price-range")
    public ResponseEntity<APIResponse> findAllByPriceRange(
            @RequestParam(value = "min") BigDecimal min,
            @RequestParam(value = "max") BigDecimal max,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return createUnifiedResponse(productFilterService.filterByPriceRange(min, max, page, size), HttpStatus.OK);
    }
}