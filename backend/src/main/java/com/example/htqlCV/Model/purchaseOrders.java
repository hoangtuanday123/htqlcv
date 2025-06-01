package com.example.htqlCV.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_purchase_orders")
public class purchaseOrders extends abstractEntity{
    @Column(name="totalAmount")
    private Long totalAmount;
    @Column(name="totalAmountPaid")
    private Long totalAmountPaid;
    @Column(name="status")
    private String status;
    @Column(name="Sub_status")
    private String SubStatus;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = true)
    @JsonManagedReference
    private supplier supplier;

    @OneToMany(mappedBy = "purchaseOrders")
    @JsonBackReference
    private List<purchaseOrderItems> purchaseOrderItems;

    @ManyToOne()
    @JoinColumn(name = "business_id")
    @JsonBackReference
    private business business;
}
