package au.com.jcloud.security;

import java.security.Principal;

/**
 * Created by david.vittor on 26/08/16.
 */
public class UserPrincipal implements Principal {

	private String name;

	public UserPrincipal(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}