package au.com.javacloud.dao;

/**
 * Created by david on 22/05/16.
 */
import java.util.List;

import au.com.javacloud.model.BaseBean;

public interface BaseDAO<T extends BaseBean> {
    public void add(T bean);
    public void update(T bean);
    public List<T> getAll();
    public T get(int Id);
    public void delete(int beanId);
}
