package au.com.jcloud.service;

import com.avaje.ebean.Ebean;

import au.com.jcloud.emodel.BaseBean;
import au.com.jcloud.emodel.Status;

/**
 * Created by david.vittor on 5/08/16.
 */
public class StatusService {

	public void enable(Class<? extends BaseBean> classType, Integer id) {
		setStatus(classType, id, Status.ENABLED);
	}

	public void disable(Class<? extends BaseBean> classType, Integer id) {
		setStatus(classType, id, Status.DISABLED);
	}

	public void setStatus(Class<? extends BaseBean> classType, Object id, Status status) {
		BaseBean bean = Ebean.find(classType).select("id, status").where().idEq(id).findUnique();
		if (bean != null) {
			bean.setStatus(status.name());
			Ebean.save(bean);
		}
	}
}
