package com.featuriz.sbm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.featuriz.sbm.exception.ResourceNotFoundException;
import com.featuriz.sbm.model.Employee;
import com.featuriz.sbm.repository.EmployeeRepository;

@Controller
public class DefaultController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/")
	public String listPage(Model model) {
//		model.addAttribute("listEmployees", employeeRepository.findAll());
//		return "frontend/home";
//		return findPaginated(1, model);
		return findPaginated(1, "firstName", "asc", model);
	}

	@GetMapping("/new")
	public String showPage(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "frontend/new";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeRepository.save(employee);
		return "redirect:/";
	}

	@GetMapping("/update/{id}")
	public String updatePage(@PathVariable(value = "id") Long employeeId, Model model)
			throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "frontend/update";
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable(value = "id") Long employeeId) {

		// call delete employee method
		this.employeeRepository.deleteById(employeeId);
		return "redirect:/";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {

		int pageSize = 3;

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

		Page<Employee> page = employeeRepository.findAll(pageable);
		List<Employee> listEmployees = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listEmployees", listEmployees);
		return "frontend/home";
	}

}
