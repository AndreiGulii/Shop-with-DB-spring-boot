package ait.cohort63.shop.service.interfaces;

import ait.cohort63.shop.model.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllActiveCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    CustomerDTO deleteCustomerById(Long id);

    CustomerDTO deleteCustomerByName(String title);

    CustomerDTO restoreCustomerById(Long id);

    long getCustomerCount();

}
