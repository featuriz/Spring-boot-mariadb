package com.featuriz.sbm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.featuriz.sbm.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	/**
	 * Returns all entities sorted by the given options.
	 *
	 * @param sort
	 * @return all entities sorted by the given options
	 */
	Iterable<Employee> findAll(Sort sort);

	/**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in
	 * the {@code Pageable} object.
	 *
	 * @param pageable
	 * @return a page of entities
	 */
	Page<Employee> findAll(Pageable pageable);
}
