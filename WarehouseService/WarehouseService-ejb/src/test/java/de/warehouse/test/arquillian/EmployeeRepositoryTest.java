package de.warehouse.test.arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.persistence.Employee;
import de.warehouse.persistence.Role;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.IEmployeeRepository;
import de.warehouse.test.ArquillianTestWithSessionsBase;

@RunWith(Arquillian.class)
public class EmployeeRepositoryTest extends ArquillianTestWithSessionsBase {

	private final int VALID_EMPLOYEE_ID = 1;
	private final int VALID_EMPLOYEE_ID_FOR_DELETE = 4;
	
	@EJB
	private IEmployeeRepository employeeRepository;

	@Test(expected = AccessDeniedException.class)
	public void testGetAllWithUnsufficientRights1() throws AccessDeniedException {
		try {
			this.employeeRepository.getAll(super.sessionIdOfKommissionierer);
		} catch (SessionExpiredException e) {
			fail(e.getMessage());
		}
	}
	@Test(expected = AccessDeniedException.class)
	public void testGetAllWithUnsufficientRights2() throws AccessDeniedException {
		try {
			this.employeeRepository.getAll(super.sessionIdOfLagerist);
		} catch (SessionExpiredException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(1)
	public void testGetAllAfterDatabaseInit() {
		try {
			Integer numberOfEmployees = this.employeeRepository.getAll(super.sessionIdOfAdministrator).size();
			assertEquals("There must be 5 employees after database init in the database.", 5, numberOfEmployees, 0);
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(2)
	public void testCreateNewEmployee() {
		Employee e = new Employee();

		e.setFirstName("Jon");
		e.setLastName("Doe");
		e.setMailAddress("jd@isp.de");
		e.setPassword("geheim");
		e.setRole(Role.Kommissionierer);

		e = this.employeeRepository.create(e);

		e = this.employeeRepository.findById(e.getCode());
		
		assertNotNull(e);
	}

	@Test
	@InSequence(3)
	public void testUpdateOfEmployee() {
		Employee e = this.employeeRepository.findById(VALID_EMPLOYEE_ID);
		
		e.setFirstName("Jane");
		e.setMailAddress("jane.doe@isp.de");
		
		e = this.employeeRepository.update(e);
		
		assertNotNull(e);
		
		e = this.employeeRepository.findById(VALID_EMPLOYEE_ID);
		
		assertEquals("Jane", e.getFirstName());
		assertEquals("jane.doe@isp.de", e.getMailAddress());
	}

	@Test
	@InSequence(4)
	public void testDeleteOfEmployee() {
		Employee e = this.employeeRepository.findById(VALID_EMPLOYEE_ID_FOR_DELETE);
		
		assertNotNull(e);
		
		this.employeeRepository.delete(e);
		
		assertNull(this.employeeRepository.findById(VALID_EMPLOYEE_ID_FOR_DELETE));
	}
}
