package com.tranv.d7shop.services.impl;

import com.tranv.d7shop.dtos.OrderDTO;
import com.tranv.d7shop.exceptions.DataNotFoundException;
import com.tranv.d7shop.models.Order;
import com.tranv.d7shop.models.OrderStatus;
import com.tranv.d7shop.models.User;
import com.tranv.d7shop.reponse.OrderRepose;
import com.tranv.d7shop.repository.OrderRepository;
import com.tranv.d7shop.repository.UserRepository;
import com.tranv.d7shop.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderRepose createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User existingUser = userRepository.findById(
                orderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find user with: " + orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippedDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();

        if (shippedDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippedDate);
        order.setActive(true);
        orderRepository.save(order);
        modelMapper.typeMap(OrderDTO.class, OrderRepose.class);
        OrderRepose orderRepose = new OrderRepose();
        modelMapper.map(order, orderRepose);
        return orderRepose;
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public Order updateOrder(long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with id: " + orderId));
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id).orElseThrow(null);
    }

    @Override
    public void deleteOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(null);
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return orderRepository.findByUserId(userId);
    }
}
