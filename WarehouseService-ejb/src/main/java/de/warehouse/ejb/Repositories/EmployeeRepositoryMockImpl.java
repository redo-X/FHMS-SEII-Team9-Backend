package de.warehouse.ejb.Repositories;

import javax.ejb.Remote;
import javax.ejb.Singleton;

import de.warehouse.shared.Employee;
import de.warehouse.shared.interfaces.IEmployeeRepository;

@Singleton
@Remote(IEmployeeRepository.class)
public class EmployeeRepositoryMockImpl implements IEmployeeRepository {

	public EmployeeRepositoryMockImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Employee GetByCode(Integer code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee[] GetAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee Create(Employee employee) {
		// TODO Auto-generated method stub
		return null;
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
