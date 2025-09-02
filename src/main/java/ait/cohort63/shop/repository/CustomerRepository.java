package ait.cohort63.shop.repository;

import ait.cohort63.shop.model.entity.Customer;
import ait.cohort63.shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //Eto vse tem samim v JPA vstroieni vse metodi CRUD, takim obrazom repositorii dlea Product gotov.
    // Vse chto dlea etogo nujno eto unasledovatisea ot JpaRepository
}
