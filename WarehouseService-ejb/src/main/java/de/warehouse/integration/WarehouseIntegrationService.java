package de.warehouse.integration;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import de.warehouse.dto.requests.LoginRequest;
import de.warehouse.dto.requests.LogoutRequest;
import de.warehouse.dto.responses.LoginResponse;
import de.warehouse.dto.responses.LogoutResponse;
import de.warehouse.shared.Employee;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;
import de.warehouse.shared.interfaces.IEmployeeRepository;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * @author David
 */
@WebService
@Stateless
public class WarehouseIntegrationService {

	@EJB
	private IEmployeeRepository employeeRepository;
	
	@EJB
	private ISessionManagement sessionManagement;
	
	
	public LoginResponse Login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		
		try {
			int sessionId = this.sessionManagement.createSession(request.getEmployeeNr(), request.getPassword());
			Employee e = this.employeeRepository.GetByCode(request.getEmployeeNr());
			
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
	public LogoutResponse Logout(LogoutRequest request) {
		LogoutResponse response = new LogoutResponse();
		
		try {
			this.sessionManagement.closeSession(request.getSessionId());
		}
		catch (Exception ex){
			response.setResultCode(-1);
			response.setResultMessage(ex.getMessage());
		}
		
		return response;
	}
	
}
