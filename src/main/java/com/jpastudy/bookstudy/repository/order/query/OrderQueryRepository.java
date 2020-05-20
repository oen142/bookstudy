package com.jpastudy.bookstudy.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {

        List<OrderQueryDto> result = findOrders();

        for (OrderQueryDto order : result) {
            List<OrderItemQueryDto> orderItems = findOrderItems(order.getOrderId());
            order.setOrderItems(orderItems);
        }
        return result;
    }

    public List<OrderQueryDto> findAllByDto_optimization() {


        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = result.stream().map(o -> o.getOrderId())
                .collect(Collectors.toList());
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new com.jpastudy.bookstudy.repository.order.query.OrderItemQueryDto(oi.order.id , oi.item.name , oi.orderPrice , oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));

        for (OrderQueryDto orderQueryDto : result) {
            orderItemMap.get(orderQueryDto.getOrderId());
        }
        return result;

    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new com.jpastudy.bookstudy.repository.order.query.OrderItemQueryDto(oi.order.id , oi.item.name , oi.orderPrice , oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId).getResultList();

    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new com.jpastudy.bookstudy.repository.order.query.OrderQueryDto(o.id , m.name , o.orderDate , o.status , d.address) from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new " +
                        " com.jpastudy.bookstudy.repository.order.query.OrderFlatDto(o.id , m.name ,o.orderDate , o.status , d.address , i.name , oi.orderPrice , oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class)
                .getResultList();


    }
}
