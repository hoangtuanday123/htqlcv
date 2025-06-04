package com.example.htqlCV.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.response.RevenueByProductAndMonth;
import com.example.htqlCV.DAO.response.dashboardResponse;
import com.example.htqlCV.Service.customerServices;
import com.example.htqlCV.Service.productServices;
import com.example.htqlCV.Service.purchaseOrdersServices;
import com.example.htqlCV.Service.saleOrdersServices;
import com.example.htqlCV.Service.supplierServices;
import com.example.htqlCV.Service.userSevices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class dashboardController {
    private final customerServices customerServices;
    private final userSevices userSevices;
    private final supplierServices supplierServices;
    private final saleOrdersServices saleOrdersServices;
    private final purchaseOrdersServices purchaseOrdersServices;
    private final productServices productServices;
    @GetMapping
    public dashboardResponse getDashBoard(@RequestParam UUID businessId) {
        Integer totalCustomer=customerServices.countCustomerByBusiness(businessId);
        Integer totalStaff=userSevices.countUserByBusiness(businessId);
        Integer totalSupplier=supplierServices.countSupplierByBusiness(businessId);
        Long totalAmoundThisMonth=saleOrdersServices.getTotalAmountThisMonthByBusinessId(businessId);
        Long totalAmoundPurchaseThisMonth=purchaseOrdersServices.getTotalAmountThisMonthByBusinessId(businessId);
        Long profitThisMonth=saleOrdersServices.getMonthlyProfitByBusinessId(businessId);
        Integer stock=productServices.getTotalStockQuantityByBusinessId(businessId);
        dashboardResponse dashboard=new dashboardResponse();
        dashboard.setTotalCustomer(totalCustomer);
        dashboard.setTotalStaff(totalStaff);
        dashboard.setTotalSupplier(totalSupplier);
        dashboard.setTotalAmoundThisMonth(totalAmoundThisMonth);
        dashboard.setTotalAmoundPurchaseThisMonth(totalAmoundPurchaseThisMonth);
        dashboard.setTotalProfit(profitThisMonth);
        dashboard.setStockQuantity(stock);
        return dashboard;
    }

    @GetMapping("/revenueMonthly")
    public List<RevenueByProductAndMonth> getMonthlyRevenueNative(@RequestParam UUID businessId) {
        int currentYear = LocalDate.now().getYear();
        return saleOrdersServices.getMonthlyRevenueNative(currentYear, businessId);
    }
    
    

}
