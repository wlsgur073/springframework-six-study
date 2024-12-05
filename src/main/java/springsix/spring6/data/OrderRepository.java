package springsix.spring6.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import springsix.spring6.order.Order;

public class OrderRepository {
    private final EntityManagerFactory emf;

    public OrderRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // 만약 DB 서버 문제가 생겨서 예외가 발생하면 어떻게 해결할 것인가? `try-catch/if`문을 잘 쓰자.
        // 만약 이러한 패턴이 몇 번이고 사용된다고 하면 `템플릿`을 사용하자.
        try {
            em.persist(order);
            em.flush();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
