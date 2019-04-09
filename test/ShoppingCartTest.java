import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBalance() throws ProductNotFoundException {
        Product product1 = new Product("title1",5);
        Product product2 = new Product("title2",7.25);
        assertEquals(0.0, cart.getBalance());
        cart.addItem(product1);
        assertEquals(product1.getPrice(), cart.getBalance());
        cart.addItem(product2);
        assertEquals(product1.getPrice() + product2.getPrice(), cart.getBalance());
        cart.removeItem(product1);
        assertEquals(product2.getPrice(), cart.getBalance());
        cart.removeItem(product2);
        assertEquals(0.0, cart.getBalance());
    }

    @Test
    void addItem() {
        double previousBalance = cart.getBalance();
        cart.addItem(new Product("title",5.1));
        assertEquals(cart.getBalance(),5.1+previousBalance,0.00001);
    }

    @Test
    void removeItem() {
        Product product1 = new Product("title1",5);
        Product product2 = new Product("title2",5);
        Product product3 = new Product("title3",5);
        Product product4 = new Product("title2",5);
        cart.addItem(product1);
        cart.addItem(product2);
        try{
            cart.removeItem(product1);
        } catch(ProductNotFoundException ex){
            fail();
        }
        try{
            cart.removeItem(product3);
            fail();
        } catch(ProductNotFoundException ignored){}
        try{
            cart.removeItem(product4);
            fail();
        } catch (ProductNotFoundException ignored){}
        try{
            cart.removeItem(product2);
        } catch (ProductNotFoundException ex){
            fail();
        }
        try{
            cart.removeItem(product2);
            fail();
        } catch(ProductNotFoundException ignored){}

    }

    @Test
    void getItemCount() {
        assertEquals(cart.getItemCount(),0);
        cart.addItem(new Product("title",5));
        assertEquals(cart.getItemCount(),1);
    }

    @Test
    void empty() {
    }
}