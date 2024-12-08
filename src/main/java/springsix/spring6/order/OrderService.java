package springsix.spring6.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PlatformTransactionManager transactionManager;

    public OrderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {
        Order order = new Order(no, total);

        this.orderRepository.save(order);
        return order;
    }

    public List<Order> createOrders(List<OrderReq> reqs) {
        // `Transaction`이 하나로 묶여 있지 않으면, 각각의 `createOrder()`가 실행될때마다 트랜잭션이 발생하기에
        // `autocommit`으로 인해 중간에 `Exception`이 발생하더라도 이전에 실행된 것들은 `commit`이 되버리는 문제가 발생한다.
        return new TransactionTemplate(transactionManager).execute(status ->
            reqs.stream().map(req -> createOrder(req.no(), req.total())).toList());
    }
}
