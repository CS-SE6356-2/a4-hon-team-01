import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ShoppingCartTest {
	private ShoppingCart cart;

	@BeforeEach
	void setUp() {
		cart = new ShoppingCart();
	}

	@AfterEach
	void tearDown() {
	}

	// after empty() is called, getItemCount() should return 0
	@Test
	void emptyTest() {
		final int count = 20; // how many products to make for the test
		Product[] products = new Product[count];
		for (int i = 0; i < count; ++i) {
			products[i] = new Product("title" + i, i);
		}
		// add items to cart, clear cart, then check if getItemCount() == 0
		for (int max = 0; max <= count; ++max) {
			for (int i = 0; i < max; ++i) {
				cart.addItem(products[i]);
			}
			cart.empty();
			assertEquals(cart.getItemCount(), 0);
		}
		// check if getItemCount() == 0 after multiple successive calls
		assertEquals(cart.getItemCount(), 0);
		assertEquals(cart.getItemCount(), 0);
		cart.empty();
		cart.empty();
		assertEquals(cart.getItemCount(), 0);
		assertEquals(cart.getItemCount(), 0);
	}

	// after a new product is added/removed, the return value of getBalance() should increase/decrease by the product's weight
	@Test
	void getBalanceTest() throws ProductNotFoundException {
		Product product1 = new Product("title1", 5);
		Product product2 = new Product("title2", 7.25);
		assertEquals(0.0, cart.getBalance(), 1e-6);
		cart.addItem(product1);
		assertEquals(product1.getPrice(), cart.getBalance(), 1e-6);
		cart.addItem(product2);
		assertEquals(product1.getPrice() + product2.getPrice(), cart.getBalance(), 1e-6);
		cart.removeItem(product1);
		assertEquals(product2.getPrice(), cart.getBalance(), 1e-6);
		cart.removeItem(product2);
		assertEquals(0.0, cart.getBalance(), 1e-6);
	}

	// after a product is added, the return value of getBalance() should increase by the product's weight
	@Test
	void addItemTest() {
		double previousBalance = cart.getBalance();
		cart.addItem(new Product("title", 5.1));
		assertEquals(cart.getBalance(), 5.1 + previousBalance, 1e-6);
	}

	// after a product is successfully removed, the return value of getItemCount() should decrease
	@Test
	void removeItemTest() {
		final int count = 20; // how many products to make for the test
		List<Product> products = new ArrayList<>(count);
		for (int i = 0; i < count; ++i) {
			products.add(new Product("title" + i, i));
			cart.addItem(products.get(i));
		}
		// remove items from the cart in a random order, checking that getItemCount() decreases each time
		Collections.shuffle(products);
		while (!products.isEmpty()) {
			try {
				int cartSize = cart.getItemCount();
				assertEquals(cart.getItemCount(), cartSize); // getItemCount() should not change if an item is not removed
				cart.removeItem(products.remove(products.size() - 1));
				assertTrue(cart.getItemCount() < cartSize);
			} catch (ProductNotFoundException ex) {
				fail("failed to remove a product from the cart");
			}
		}
	}

	// if a product not in the cart is removed, a ProductNotFoundException should be thrown
	@Test
	void productNotFoundExceptionTest() {
		Product product1 = new Product("title1", 5);
		Product product2 = new Product("title2", 7.25);
		Product product3 = new Product("title3", 8.5);
//		Product product4 = new Product("title2",5);
		cart.addItem(product1);
		cart.addItem(product2);
		try {
			cart.removeItem(product1);
		} catch (ProductNotFoundException ex) {
			fail("failed to remove product1 from the cart");
		}
		try {
			cart.removeItem(product3);
			fail("removed product3 from the cart, when it was never added to the cart");
		} catch (ProductNotFoundException ignored) { }
//		try {
//			cart.removeItem(product4);
//			fail();
//		} catch (ProductNotFoundException ignored){}
		try {
			cart.removeItem(product2);
		} catch (ProductNotFoundException ex) {
			fail("failed to remove product2 from the cart");
		}
		try {
			cart.removeItem(product2);
			fail("removed product1 from the cart, after it had already been removed");
		} catch (ProductNotFoundException ignored) { }

	}

	// after the cart is created, getItemCount() should return 0
	// once products are added to the cart, getItemCount() should not return 0
	@Test
	void getItemCountTest() {
		assertEquals(cart.getItemCount(), 0);
		cart.addItem(new Product("title", 5));
		assertEquals(cart.getItemCount(), 1);
	}

	// after a product is added, the return value of getItemCount() should increase
	@Test
	public void newItemAmountTest(){
		assertEquals(cart.getItemCount(), 0);
		cart.addItem(new Product("title", 5));
		assertEquals(cart.getItemCount(), 1);
	}

	// after a new product is added, the return value of getBalance() should increase by the product's weight
	@Test
	public void newBalanceTest() {
		double previousBalance = cart.getBalance();
		cart.addItem(new Product("title", 5.1));
		assertEquals(cart.getBalance(), 5.1 + previousBalance, 1e-6);
	}
}