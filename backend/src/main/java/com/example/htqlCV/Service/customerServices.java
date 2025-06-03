package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.customerRequestDTO;
import com.example.htqlCV.Model.customer;
public interface  customerServices {
    List<customer> getCustomers(UUID businessId);
    customer getCustomer(UUID id);
    UUID createCustomer(customerRequestDTO customer);
    void updateCustomer(UUID id, customerRequestDTO customer);
    void deleteCustomer(UUID id);
    Integer countCustomerByBusiness(UUID businessId);
}
