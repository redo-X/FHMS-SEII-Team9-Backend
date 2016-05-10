package de.warehouse.employee;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;

import de.warehouse.shared.Employee;
import de.warehouse.shared.interfaces.IEmployeeRepository;

@Stateless
@Remote(IEmployeeRepository.class)
public class EmployeeRepository implements IEmployeeRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Employee GetByCode(Integer code) {
		// TODO Auto-generated method stub
		return this.em.find(Employee.class, code);
	}

	@Override
	public Employee[] GetAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee Create(Employee employee) {
		this.em.persist(employee);
		return employee;
	}

	@Override
	public Employee Update(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee Remove(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}
