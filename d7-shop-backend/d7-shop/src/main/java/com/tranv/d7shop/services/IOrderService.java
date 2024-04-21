package com.tranv.d7shop.services;

import com.tranv.d7shop.dtos.OrderDTO;
import com.tranv.d7shop.exceptions.DataNotFoundException;
import com.tranv.d7shop.models.Order;
import com.tranv.d7shop.reponse.OrderRepose;

import java.util.List;

public interface IOrderService {
    OrderRepose createOrder(OrderDTO orderDTO) throws Exception;

    List<Order> getAllOrders();

    Order updateOrder(long orderId, OrderDTO orderDTO) throws Exception;

    Order findById(long id);

    void deleteOrderById(long id);

    List<Order> findByUserId(long userId);
}
