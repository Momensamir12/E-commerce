package webdev.e_commerce.cart_service.repository;

import org.springframework.data.repository.CrudRepository;
import webdev.e_commerce.cart_service.model.Cart;

public interface CartRepository extends CrudRepository<Cart, String> {

}
