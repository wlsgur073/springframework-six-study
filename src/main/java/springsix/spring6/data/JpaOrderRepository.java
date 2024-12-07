package springsix.spring6.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import springsix.spring6.order.Order;
import springsix.spring6.order.OrderRepository;

public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Order order) {
        em.persist(order);
    }
}
