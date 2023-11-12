package domain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {

    private Engine engine;
    private Order order;

    @BeforeEach
    public void setUp() {
        engine = new Engine();
        order = new Order();
        order.setId(1);
        order.setCustomer(1);
        order.setPrice(5000);
        order.setQuantity(5);
    }

    @AfterEach
    public void tearDown() {
        engine = null;
        order = null;
    }

    @Test
    public void testAddOrderAndGetFraudulentQuantityWithEmptyOrderHistory() {
        int fraudulentQuantity = engine.addOrderAndGetFraudulentQuantity(order);
        assertEquals(5, fraudulentQuantity);
    }

    @Test
    public void testGetAverageOrderQuantityByCustomerNoOrdersForCustomer() {
        int averageQuantity = engine.getAverageOrderQuantityByCustomer(1);
        assertEquals(0, averageQuantity);
    }

    @Test
    public void testGetAverageOrderQuantityByCustomerSingleOrderForCustomer() {
        engine.addOrderAndGetFraudulentQuantity(order);
        int averageQuantity = engine.getAverageOrderQuantityByCustomer(1);
        assertEquals(5, averageQuantity);
    }

    @Test
    public void testGetQuantityPatternByPriceWithNoOrders() {
        int pattern = engine.getQuantityPatternByPrice(50);
        assertEquals(0, pattern);
    }

    @Test
    public void testGetQuantityPatternByPriceWithValidPattern() {
        Order order1 = new Order();
        order1.setId(2);
        order1.setPrice(50);
        order1.setQuantity(10);

        Order order2 = new Order();
        order2.setId(3);
        order2.setPrice(50);
        order2.setQuantity(15);

        engine.addOrderAndGetFraudulentQuantity(order1);
        engine.addOrderAndGetFraudulentQuantity(order2);

        int pattern = engine.getQuantityPatternByPrice(50);
        assertEquals(5, pattern);
    }

    @Test
    public void testGetQuantityPatternByPriceNoPatternFound() {
        engine.addOrderAndGetFraudulentQuantity(order);

        int pattern = engine.getQuantityPatternByPrice(60);
        assertEquals(0, pattern);
    }

    @Test
    public void testGetQuantityPatternByPriceWithPatternNotConsistent() {
        Order order1 = new Order();
        order1.setId(2);
        order1.setPrice(50);
        order1.setQuantity(10);

        Order order2 = new Order();
        order2.setId(3);
        order2.setPrice(50);
        order2.setQuantity(15);

        engine.addOrderAndGetFraudulentQuantity(order1);
        engine.addOrderAndGetFraudulentQuantity(order2);

        Order order3 = new Order();
        order3.setId(4);
        order3.setPrice(50);
        order3.setQuantity(18);

        engine.addOrderAndGetFraudulentQuantity(order3);

        int pattern = engine.getQuantityPatternByPrice(50);
        assertEquals(0, pattern);
    }

    @Test
    public void testAddOrderAndGetFraudulentQuantityOrderAlreadyExists() {
        engine.addOrderAndGetFraudulentQuantity(order);
        int fraudulentQuantity = engine.addOrderAndGetFraudulentQuantity(order);
        assertEquals(0, fraudulentQuantity);
    }

    @Test
    public void testAddOrderAndGetFraudulentQuantityWithInvalidOrder() {
        order.setId(2);
        int fraudulentQuantity = engine.addOrderAndGetFraudulentQuantity(order);
        assertEquals(5, fraudulentQuantity);
    }

    @Test
    public void testAddOrderAndGetFraudulentQuantityWithZeroQuantity() {
        order.setQuantity(0);
        int fraudulentQuantity = engine.addOrderAndGetFraudulentQuantity(order);
        assertEquals(0, fraudulentQuantity);
    }

    @Test
    public void testGetAverageOrderQuantityByCustomerWithMultipleOrdersForCustomer() {
        Order order1 = new Order();
        order1.setId(2);
        order1.setCustomer(1);
        order1.setQuantity(10);

        Order order2 = new Order();
        order2.setId(3);
        order2.setCustomer(1);
        order2.setQuantity(15);

        engine.addOrderAndGetFraudulentQuantity(order1);
        engine.addOrderAndGetFraudulentQuantity(order2);

        int averageQuantity = engine.getAverageOrderQuantityByCustomer(1);
        assertEquals(12, averageQuantity);
    }

    @Test
    public void testGetAverageOrderQuantityByCustomerWithCustomerMismatchDivisionByZero() {
        Order order1 = new Order();
        order1.setId(2);
        order1.setCustomer(2);
        order1.setQuantity(10);

        engine.addOrderAndGetFraudulentQuantity(order1);
        assertThrows(ArithmeticException.class, () -> engine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void testGettersAndSettersOfOrderClass() {
        Order order = new Order();
        order.setId(1);
        order.setCustomer(1);
        order.setPrice(5000);
        order.setQuantity(5);

        assertEquals(1, order.getId());
        assertEquals(1, order.getCustomer());
        assertEquals(5000, order.getPrice());
        assertEquals(5, order.getQuantity());
    }

    @Test
    public void testEqualsWithNonOrderObject() {
        Order order = new Order();
        order.setId(1);

        assertFalse(order.equals("not an Order object"));
        assertFalse(order.equals(null));
    }
}