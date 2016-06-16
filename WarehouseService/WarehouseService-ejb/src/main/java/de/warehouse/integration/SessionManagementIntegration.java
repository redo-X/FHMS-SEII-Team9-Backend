package de.warehouse.integration;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import de.warehouse.dto.responses.session.LoginResponse;
import de.warehouse.dto.responses.session.LogoutResponse;
import de.warehouse.persistence.Employee;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;
import de.warehouse.shared.interfaces.IEmployeeRepository;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * @author David
 */
@WebService
@Stateless
public class SessionManagementIntegration {

	@EJB
	private IEmployeeRepository employeeRepository;
	
	@EJB
	private ISessionManagement sessionManagement;
	
	
	public LoginResponse login(int employeeNr, String password) {
		LoginResponse response = new LoginResponse();

		try {
			int sessionId = this.sessionManagement.createSession(employeeNr, password);
			Employee e = this.employeeRepository.findById(employeeNr);
			
			response.setSessionId(sessionId);
			response.setRole(e.getRole().ordinal());
		}catch(EntityNotFoundException ex) {
			response.setResultCode(-1);
			response.setResultMessage(ex.getMessage());
		}catch(UsernamePasswordMismatchException ex) {
			response.setResultCode(-2);
			response.setResultMessage(ex.getMessage());
		}catch(Exception ex) {
			response.setResultCode(-3);
			response.setResultMessage(ex.getMessage());
		}
		
		return response;
	}
	public LogoutResponse logout(int sessionId) {
		LogoutResponse response = new LogoutResponse();
		
		try {
			this.sessionManagement.closeSession(sessionId);
		}
		catch (Exception ex){
			response.setResultCode(-1);
			response.setResultMessage(ex.getMessage());
		}
		
		return response;
	}
}