package ait.cohort63.shop.service.interfaces;

import ait.cohort63.shop.model.entity.Customer;
import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    List<Customer> getAllActiveCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id, Customer customer);

    Customer deleteCustomerById(Long id);

    Customer deleteCustomerByName(String title);

    Customer restoreCustomerById(Long id);

    long getCustomerCount();

}
