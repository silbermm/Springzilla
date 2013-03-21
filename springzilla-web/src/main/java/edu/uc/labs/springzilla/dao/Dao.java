package edu.uc.labs.springzilla.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

public interface Dao<T extends Object> {
	
	void create(T t);
	T get(Serializable id);
	T load(Serializable id);
	List<T> getAll();
	void update(T t);
	void delete(T t);
	void deleteById(Serializable id);
	long count();
	boolean exists(Serializable id);
	void setSessionFactory(SessionFactory sessionFactory);
}
