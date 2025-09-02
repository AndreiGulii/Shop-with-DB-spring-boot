package ait.cohort63.shop.service;

import ait.cohort63.shop.model.dto.CustomerDTO;
import ait.cohort63.shop.model.entity.Customer;
import ait.cohort63.shop.repository.CustomerRepository;
import ait.cohort63.shop.service.interfaces.CustomerService;
import ait.cohort63.shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMappingService mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMappingService mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.mapDTOToEntity(customerDTO);
        customer.setActive(true);
        return mapper.mapEntityToDTO(customerRepository.save(customer));
    }

    @Override
    public List<CustomerDTO> getAllActiveCustomers() {
        // Обращаемся к репозиторию
        return customerRepository.findAll()
                .stream()
                .filter(Customer::isActive)
                .map(mapper::mapEntityToDTO)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        // Обращаемся к репозиторию
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null || !customer.isActive()){
            return null;
        }
        return mapper.mapEntityToDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public CustomerDTO deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public CustomerDTO deleteCustomerByName(String title) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public CustomerDTO restoreCustomerById(Long id) {
        // Обращаемся к репозиторию
        return null;
    }

    @Override
    public long getCustomerCount() {
        // Обращаемся к репозиторию
        return 0;
    }
}