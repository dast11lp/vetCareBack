package io.demo.veterinary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class BaseService <E, R extends JpaRepository<E, Long>>{
	
	@Autowired
	private R repository;
	
	public List<E> findAll(){
		return this.repository.findAll();
	}
	
	public E save(E entity)  {
		return this.repository.save(entity);
	}
	
	public E findById(Long id) {
		return this.repository.findById(id).orElse(null);
	}
	
	public E updatebyId(Long id) {
		E entity = this.findById(id);
		return this.repository.save(entity);
	}
	
	public E deleteById (Long id) {
		return this.deleteById(id);
	}
}
