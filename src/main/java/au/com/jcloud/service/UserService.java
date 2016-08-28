package au.com.jcloud.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;

import au.com.jcloud.emodel.Status;
import au.com.jcloud.emodel.User;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * Created by david on 5/08/16.
 */
public class UserService extends BaseService {
	
	@SpringBean
	private EncryptService encryptService;
	@SpringBean
	private PropertyReaderService propertyReaderService;
	
	private static String realm;

	public User createUser(String username, String firstName, String lastName, String email, String password) throws Exception {
		User user = getByUsername(username);
		if (user != null) {
			throw new Exception("Username already exists! Please select a different one");
		}
		user = getByEmail(email);
		if (user != null) {
			throw new Exception("This email is already registered! Please select a different one");
		}
		user = new User();
		user.setName(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		String passValue = getPassword(username, password);
		user.setPassword(passValue);
		user.setStatus(Status.ENABLED.name());
		LOG.info("creating new user: " + user);

		Ebean.save(user);
		// EBeanServerService.getEBeanServer().save(user);
		return user;
	}

	private String getPassword(String username, String password) throws IOException {
		String uservalue = username+":"+password;
		return encryptService != null ? encryptService.md5(uservalue) : password;
	}

	public User updatePassword(String username, String password) throws Exception {
		User user = getByUsername(username);
		if (user == null) {
			throw new Exception("Could not find user: " + username);
		}
		String passValue = getPassword(username, password);
		user.setPassword(passValue);
		Ebean.save(user);
		user = getByUsername(username);
		return user;
	}

	public User get(int id) {
		User user = Ebean.find(User.class, id);
		return user;
	}

	public User getByUsernameOrEmail(String usernameOrEmail) {
		User user = getByUsername(usernameOrEmail);
		if (user != null) {
			return user;
		}
		user = getByEmail(usernameOrEmail);
		return user;
	}

	public User getByUsername(String username) {
		User user = Ebean.find(User.class).select("id, name, email, status, firstName, lastName").where().eq("name", username).findUnique();
		return user;
	}

	public User getByEmail(String username) {
		User user = Ebean.find(User.class).select("id, name, email, status, firstName, lastName").where().eq("email", username).findUnique();
		return user;
	}

	public List<User> getListByUsername(String username) {
		List<User> users = Ebean.find(User.class).select("id, name, email, status, firstName, lastName").where().eq("name", username).findList();
		return users;
	}

	public List<User> getListByEmail(String username) {
		List<User> users = Ebean.find(User.class).select("id, name, email, status, firstName, lastName").where().eq("email", username).findList();
		return users;
	}

	public User getUserByAuth(String username, String password) throws IOException {
		String passValue = getPassword(username, password);
		User user = null;
		if (StringUtils.isNotBlank(username)) {
			user = Ebean.find(User.class).select("id, name, email, status, firstName, lastName").where()
					.or(Expr.eq("name", username), Expr.eq("email", username)).eq("password", passValue).eq("status", Status.ENABLED.name())
					.findUnique();
		}
		return user;
	}

	public EncryptService getEncryptService() {
		return encryptService;
	}

	public void setEncryptService(EncryptService encryptService) {
		this.encryptService = encryptService;
	}

	public PropertyReaderService getPropertyReaderService() {
		return propertyReaderService;
	}

	public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
		this.propertyReaderService = propertyReaderService;
	}

	public static void main(String[] args) {
		try {
			EBeanServerService.createEbeanServer();
//			EBeanServerService service = new EBeanServerService(true);
			UserService us = new UserService();
			System.out.println("us=" + us);
			User user = us.createUser("test", "first", "last", "email", "pass");
//            user.setStatus(StatusService.Status.DISABLED.name());
//            Ebean.save(user);
			System.out.println("user=" + user);
			System.out.println("test=" + us.getByUsername("test"));
			System.out.println("te%=" + us.getByUsername("te%"));
			System.out.println("auth=" + us.getUserByAuth("test", "pass"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
