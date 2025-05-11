package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.customerRequestDTO;
import com.example.htqlCV.Model.customer;

public interface  customerServices {
    List<customer> getCustomers();
    customer getCustomer(Long id);
    Long createCustomer(customerRequestDTO customer);
    void updateCustomer(Long id, customerRequestDTO customer);
    void deleteCustomer(Long id);
}
