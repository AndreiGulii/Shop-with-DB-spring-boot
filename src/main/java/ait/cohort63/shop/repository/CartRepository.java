package ait.cohort63.shop.repository;

import ait.cohort63.shop.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
