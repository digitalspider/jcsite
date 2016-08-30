package au.com.jcloud.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by david.vittor on 5/08/16.
 */
@Entity
@Table(name = "user")
public class User extends BaseBean {
	protected String email;
	protected String password;
	protected String firstName;
	protected String lastName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	protected List<Server> serverList;

	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public String toString() {
		return super.toString() + " email=" + email + " firstName=" + firstName + " lastName=" + lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
