package com.rp.authenticationsystem.service;

public interface ICrudService<T, I> {
	
	T get(I id);

	void save(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
}
