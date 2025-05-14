package com.example.htqlCV.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "tbl_purchase_order_items")
public class purchaseOrderItems extends abstractEntity{
    @ManyToOne
    @JoinColumn(name = "purchase_orders_id")
    @JsonManagedReference
    private purchaseOrders purchaseOrders;
    

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private product product;

    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "unit_price")
    private Long unitPrice;
}
