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
	void emptyTest() {
	}

	@Test
	void getBalanceTest() throws ProductNotFoundException {
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
	void addItemTest() {
		double previousBalance = cart.getBalance();
		cart.addItem(new Product("title",5.1));
		assertEquals(cart.getBalance(),5.1+previousBalance,0.00001);
	}

	@Test
	void removeItemTest() {
		Product product1 = new Product("title1",5);
		Product product2 = new Product("title2",7.25);
		Product product3 = new Product("title3",8.5);
//		Product product4 = new Product("title2",5);
		cart.addItem(product1);
		cart.addItem(product2);
		try {
			cart.removeItem(product1);
		} catch(ProductNotFoundException ex){
			fail("failed to remove product1 from the cart");
		}
		try {
			cart.removeItem(product3);
			fail("removed product3 from the cart, when it was never added to the cart");
		} catch(ProductNotFoundException ignored){}
//		try {
//			cart.removeItem(product4);
//			fail();
//		} catch (ProductNotFoundException ignored){}
		try {
			cart.removeItem(product2);
		} catch (ProductNotFoundException ex){
			fail("failed to remove product2 from the cart");
		}
		try {
			cart.removeItem(product2);
			fail("removed product1 from the cart, after it had already been removed");
		} catch(ProductNotFoundException ignored){}

	}

	@Test
	void getItemCountTest() {
		assertEquals(cart.getItemCount(),0);
		cart.addItem(new Product("title",5));
		assertEquals(cart.getItemCount(),1);
	}
	
	@Test
	public void newItemAmountTest(){
		assertEquals(cart.getItemCount(),0);
		cart.addItem(new Product("title",5));
		assertEquals(cart.getItemCount(),1);
	}

	@Test
	public void newBalanceTest() {
		double previousBalance = cart.getBalance();
		cart.addItem(new Product("title",5.1));
		assertEquals(cart.getBalance(),5.1+previousBalance,0.00001);
	}
}