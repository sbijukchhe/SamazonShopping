package com.example.demo;

import javax.persistence.*;
import java.util.Collection;

@Entity

@Table(name="Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long OrderId;

    private boolean orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
/*
    @ManyToMany (mappedBy = "orders")
    private Collection<Product> products;
*/


    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL)
    private Collection<Product> products;

    public Order(){}

    public Order(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public long getOrderId() { return OrderId; }

    public void setOrderId(long orderId) { this.OrderId = orderId; }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }



}
