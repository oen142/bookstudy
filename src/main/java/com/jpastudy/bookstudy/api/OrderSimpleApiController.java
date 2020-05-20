package com.jpastudy.bookstudy.api;

import com.jpastudy.bookstudy.domain.Address;
import com.jpastudy.bookstudy.domain.Order;
import com.jpastudy.bookstudy.domain.OrderStatus;
import com.jpastudy.bookstudy.repository.OrderRepository;
import com.jpastudy.bookstudy.repository.OrderSearch;
import com.jpastudy.bookstudy.repository.order.query.OrderQueryDto;
import com.jpastudy.bookstudy.repository.order.simplequery.OrderSimpleQueryDto;
import com.jpastudy.bookstudy.repository.order.simplequery.OrderSimpleQueryRepository;
import com.jpastudy.bookstudy.service.query.OrderQueryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/*
* xToOne(ManyToOne ,OneToOne)
* Order
* Order -> Member
* Order -> Delivery
* */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for(Order order : all){
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;

    }
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return result;
    }
    private final OrderQueryService orderQueryService;
    @GetMapping("/api/v3/simple-orders")
    public List<OrderQueryService.OrderDto> ordersV3(){

        return orderQueryService.ordersV3();
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }


}
