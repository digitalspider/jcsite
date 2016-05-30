package au.com.javacloud.dao;

import java.text.ParseException;
/**
 * Created by david on 22/05/16.
 */
import java.util.List;

import au.com.javacloud.model.BaseBean;

public interface BaseDAO<T extends BaseBean> {
    public void add(T bean);
    public void update(T bean);
    public List<T> getAll(Class<T> clazz) throws InstantiationException, IllegalAccessException, ParseException;
    public T get(int id, Class<T> clazz) throws InstantiationException, IllegalAccessException, ParseException;
    public void delete(int beanId);
}
