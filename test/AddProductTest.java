import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AddProductTest {

	@Test
	public void newBalanceTest() {
		ShoppingCart cart = new ShoppingCart();
		double previousBalance = cart.getBalance();
		cart.addItem(new Product("title",5.1));
		assertEquals(cart.getBalance(),5.1+previousBalance,0.00001);
	}
	
	@Test
	public void newItemAmountTest(){
		ShoppingCart cart = new ShoppingCart();
		assertEquals(cart.getItemCount(),0);
		cart.addItem(new Product("title",5));
		assertEquals(cart.getItemCount(),1);
	}

}
