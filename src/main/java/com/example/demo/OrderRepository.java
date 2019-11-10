package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    //    Order findByOrderId(long id);
    Order findByOrderStatus(boolean orderStatus);
//    Order findByUser (User user);
}