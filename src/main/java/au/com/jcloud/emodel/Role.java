package au.com.jcloud.emodel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by david on 24/08/16.
 */
@Entity
@Table(name = "role")
public class Role extends IdBean {
	private String name;

	@Override
	public String toString() {
		return super.toString() + " name=" + name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
