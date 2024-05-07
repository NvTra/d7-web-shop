package com.tranv.d7shop.services.impl;

import com.tranv.d7shop.dtos.OrderDetailDTO;
import com.tranv.d7shop.exceptions.DataNotFoundException;
import com.tranv.d7shop.models.Order;
import com.tranv.d7shop.models.OrderDetail;
import com.tranv.d7shop.models.Product;
import com.tranv.d7shop.repository.OrderDetailRepository;
import com.tranv.d7shop.repository.OrderRepository;
import com.tranv.d7shop.repository.ProductRepository;
import com.tranv.d7shop.services.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(
                        () -> new DataNotFoundException(
                                "Cannot find order with id: " + orderDetailDTO.getOrderId()));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(
                        () -> new DataNotFoundException(
                                "Cannot find product with id: " + orderDetailDTO.getProductId()
                        )
                );
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProduct(orderDetailDTO.getNumberOfProducts())
                .totalMoney(order.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .price(orderDetailDTO.getPrice())
                .build();

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Cannot find order detail with id: " + id)
        );
    }

    @Override
    public List<OrderDetail> getOrderDetails() {
        return List.of();
    }


    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find order detail with id " + id)
        );
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with id " + id)
        );
        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find product with id " + id)
        );
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setNumberOfProduct(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(product);

        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }
}
