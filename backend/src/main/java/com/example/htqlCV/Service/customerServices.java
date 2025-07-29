package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.customerRequestDTO;
import com.example.htqlCV.Model.customer;
public interface  customerServices {
    List<customer> getCustomers(String businessId);
    customer getCustomer(String id);
    String createCustomer(customerRequestDTO customer);
    void updateCustomer(String id, customerRequestDTO customer);
    void deleteCustomer(String id);
    Integer countCustomerByBusiness(String businessId);
}
