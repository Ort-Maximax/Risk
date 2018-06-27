package com.ort.risk.storage.repository;

import java.util.List;

public interface IRepository<T> {

	/**
	 * Get every recording of type <code>T</code>
	 * @return List of type <code>T</code>
	 */
	List<T> getAll();
	
	/**
	 * Get the recording of type <code>T</code> with the given id
	 * @param id
	 * @return A <code>T</code> recording
	 */
	T get(Long id);
	
	/**
	 * Persist the given <code>T</code> recording in the storage
	 * @param <code>T</code> recording
	 */
	void create(T recording);
	
	/**
	 * Delete the given <code>T</code> recording from the storage
	 * @param <code>T</code> recording
	 */
	void delete(T recording);
	
	/**
	 * Update the given <code>T</code> recording in the storage
	 * @param <code>T</code> recording
	 */
	void update(T recording);
	
}
