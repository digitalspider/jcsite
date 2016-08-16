package au.com.jcloud.emodel;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by david.vittor on 5/08/16.
 */
@MappedSuperclass
public class IdBean {
	@Id
	protected Long id;

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + id + "]:";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
