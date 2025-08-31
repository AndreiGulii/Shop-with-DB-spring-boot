package ait.cohort63.shop.service;

import ait.cohort63.shop.model.entity.Customer;
import ait.cohort63.shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer saveCustomer(Customer customer) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        // Обращаемся к репозиторию
        return List.of();
    }

    @Override
    public Customer getCustomerById(Long id) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public Customer deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public Customer deleteCustomerByName(String title) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public Customer restoreCustomerById(Long id) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public long getCustomerCount() {
        // Обращаемся к репозиторию
        return 0;
    }
}
