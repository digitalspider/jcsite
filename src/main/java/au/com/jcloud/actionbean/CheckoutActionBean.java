package au.com.jcloud.actionbean;

import static au.com.jcloud.WebConstants.BILLING_JSP;
import static au.com.jcloud.WebConstants.CART_JSP;
import static au.com.jcloud.WebConstants.PATH_SECURE;
import static au.com.jcloud.WebConstants.PATH_SECURE_JSP;
import static au.com.jcloud.WebConstants.THANKYOU_JSP;
import static au.com.jcloud.actionbean.CheckoutActionBean.JSP_BINDING;

import java.util.List;

import javax.annotation.security.PermitAll;

import com.avaje.ebean.Ebean;

import au.com.jcloud.model.Cart;
import au.com.jcloud.model.CartItem;
import au.com.jcloud.model.Product;
import au.com.jcloud.model.User;
import au.com.jcloud.util.PathParts;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Created by david.vittor on 3/08/16.
 */
@PermitAll
@UrlBinding(PATH_SECURE + JSP_BINDING)
public class CheckoutActionBean extends JCSecureActionBean {

	public static final String JSP_BINDING = "/checkout";

	private Cart cart;

	public String getCartPageJsp() {
		return PATH_SECURE_JSP + getJSPBinding() + "/" + CART_JSP;
	}

	public String getBillingPageJsp() {
		return PATH_SECURE_JSP + getJSPBinding() + "/" + BILLING_JSP;
	}

	public String getThankYouPageJsp() {
		return PATH_SECURE_JSP + getJSPBinding() + "/" + THANKYOU_JSP;
	}

	public Resolution getCartResolution() {
		return new ForwardResolution(getCartPageJsp());
	}

	public Resolution getBillingResolution() {
		return new ForwardResolution(getBillingPageJsp());
	}

	public Resolution getThankYouResolution() {
		return new ForwardResolution(getThankYouPageJsp());
	}

	@Override
	public Resolution getDefaultResolution() {
		return getCartResolution();
	}

	@PermitAll
	@DontValidate
	@DontBind
	@DefaultHandler
	@Override
	public Resolution action() {
		User user = getUser();
		List<Cart> carts = user.getCarts();
		if (carts.isEmpty()) {
			cart = new Cart();
		}
		else {
			cart = carts.get(0);
		}

		PathParts pathParts = getPathParts(); // 0=checkout, 1=add, 2=123 (productId), 3=description
		LOG.info("pathParts=" + pathParts);
		if (pathParts.size() > 1) {
			String action = pathParts.get(1);
			LOG.info("action=" + action);
			switch (action) {
			case ("cart"):
				if (pathParts.size() > 2) {
					String cartAction = pathParts.get(2);
					LOG.info("cartAction=" + cartAction);
					switch (cartAction) {
					case ("add"):
						int productId = pathParts.getInt(3);
						LOG.info("adding productId=" + productId);
						if (productId > 0) {
							Product product = Ebean.find(Product.class).where().idEq(Long.valueOf(productId)).findUnique();
							LOG.info("adding product=" + product);
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setProduct(product);
								cartItem.setQuantity(1);
								cart.getCartItems().add(cartItem);
								Ebean.save(cart);
							}
						}
					}
				}
				return getCartResolution();
			case ("billing"):
				LOG.info("Need to validate cart()");
				return getBillingResolution();
			case ("purchase"):
				LOG.info("Need to validate cart()");
				return getThankYouResolution();
			}
		}
		return getDefaultResolution();
	}

	@HandlesEvent("checkout")
	public Resolution checkout() throws Exception {
		User user = getUser();
		LOG.info(user + " has checked out!");
		return getBillingResolution();
	}

	@HandlesEvent("purchase")
	public Resolution purchase() throws Exception {
		User user = getUser();
		LOG.info(user + " has bought!");
		return getThankYouResolution();
	}

	@Override
	public String getJSPBinding() {
		return JSP_BINDING;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

}
