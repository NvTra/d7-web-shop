package com.tranv.d7shop.services;

import com.tranv.d7shop.dtos.OrderDetailDTO;
import com.tranv.d7shop.exceptions.DataNotFoundException;
import com.tranv.d7shop.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    OrderDetail getOrderDetail(Long id) throws Exception;

    List<OrderDetail> getOrderDetails();

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    void deleteOrderDetail(Long orderDetailId);

    List<OrderDetail> findOrderDetailByOrderId(Long orderId);
}
