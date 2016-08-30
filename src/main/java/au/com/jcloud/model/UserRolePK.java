package au.com.jcloud.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Created by david.vittor on 29/08/16.
 */
@Embeddable
public class UserRolePK implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long userId;
	public Long roleId;

	public UserRolePK(Long userId, Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UserRolePK other = (UserRolePK) obj;
		if ((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId)) {
			return false;
		}
		if ((this.roleId == null) ? (other.roleId != null) : !this.roleId.equals(other.roleId)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + (this.userId != null ? this.userId.hashCode() : 0);
		hash = 89 * hash + (this.roleId != null ? this.roleId.hashCode() : 0);
		return hash;
	}
}
