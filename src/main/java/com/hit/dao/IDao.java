package main.java.com.hit.dao;

import java.io.IOException;
import java.io.Serializable;

public interface IDao<ID extends java.io.Serializable, T> {
    public void delete(T entity);
    public T find(ID id) throws java.lang.IllegalArgumentException;
    public void save(T entity) throws java.lang.IllegalArgumentException, IOException;
}
