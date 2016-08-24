package au.com.jcloud.emodel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by david on 24/08/16.
 */
@Entity
@Table(name="userrole")
public class UserRole {
	private User user;
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
