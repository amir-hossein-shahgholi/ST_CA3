package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testEqualsWithSameOrder() {
        Order order1 = new Order();
        order1.setId(1);

        Order order2 = new Order();
        order2.setId(1);

        assertEquals(order1, order2);
    }

    @Test
    public void testEqualsWithDifferentOrder() {
        Order order1 = new Order();
        order1.setId(1);

        Order order2 = new Order();
        order2.setId(2);

        assertNotEquals(order1, order2);
    }

    @Test
    public void testEqualsWithNonOrderObject() {
        Order order = new Order();
        order.setId(1);

        assertNotEquals("not an Order object", order);
        assertNotEquals(null, order);
    }

    @Test
    public void testGettersAndSetters() {
        Order order = new Order();
        order.setId(1);
        order.setCustomer(2);
        order.setPrice(5000);
        order.setQuantity(5);

        assertEquals(1, order.getId());
        assertEquals(2, order.getCustomer());
        assertEquals(5000, order.getPrice());
        assertEquals(5, order.getQuantity());
    }

    @Test
    public void testEqualsWithDifferentClass() {
        Order order = new Order();
        order.setId(1);

        Object obj = new Object();

        assertNotEquals(order, obj);
    }
}
