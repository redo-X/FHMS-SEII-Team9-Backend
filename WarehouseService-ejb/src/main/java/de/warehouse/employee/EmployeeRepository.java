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
		return this.em.find(Employee.class, code);
	}

	@Override
	public Employee[] GetAll() {
		return (Employee[])this.em.createQuery("SELECT t FROM " + Employee.class.getSimpleName() + " t").getResultList().toArray();
	}

	@Override
	public Employee Create(Employee employee) {
		this.em.persist(employee);
		return employee;
	}

	@Override
	public Employee Update(Employee employee) {
		return this.em.merge(employee);
	}

	@Override
	public void Remove(Employee employee) {
		this.em.remove(employee);
	}

	
	
	

}
