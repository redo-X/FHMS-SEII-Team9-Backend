package de.warehouse.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import de.warehouse.shared.Employee;
import de.warehouse.shared.Role;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.IEmployeeRepository;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * @author Thoene Diese Klasse realisiert einen rudimentaeren Client zum Zugriff
 *         auf das OnlineBankingSystem.
 */
public class SimpleOnlineBankingClient {

	private static IEmployeeRepository remoteSystem;
	private static ISessionManagement sessionRemoteSystem;

	/**
	 * In dieser Main-Methode werden Requests an den Onlinebanking-Server
	 * abgeschickt. Sie koennen die durchgefuehrten Szenarien nach Belieben
	 * anpassen.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			Context context = new InitialContext();

			String lookupString = "WarehouseService-ear/WarehouseService-ejb-1.0.0/EmployeeRepository!de.warehouse.shared.interfaces.IEmployeeRepository";
			String lookupStringSession = "/WarehouseService-ear/WarehouseService-ejb-1.0.0/SessionManagement!de.warehouse.shared.interfaces.ISessionManagement";

			remoteSystem = (IEmployeeRepository) context.lookup(lookupString);
			sessionRemoteSystem = (ISessionManagement) context.lookup(lookupStringSession);

			Employee e1 = remoteSystem.GetByCode(Integer.valueOf(1));

			if (e1 == null) {
				e1 = new Employee();
				e1.setFirstName("Max");
				e1.setLastName("Mustermann");
				e1.setRole(Role.Kommissionierer);

				e1 = remoteSystem.Create(e1);
			}
			System.out.println(e1.getCode());

			int sessionId = sessionRemoteSystem.createSession(e1);
			try {
				sessionRemoteSystem.closeSession(sessionId);
				Employee[] emps = remoteSystem.GetAll(sessionId);
				for (Employee e : emps) {
					System.out.println(e.toString());
				}
			} 
			catch (SessionExpiredException see) 
			{
				System.out.println("Sitzung ist abgelaufebn");
			} 
			catch (AccessDeniedException ade) 
			{
				System.out.println("Keine Berechtigung");
			} 
			finally {
				sessionRemoteSystem.closeSession(sessionId);
			}

		} catch (Exception ex) {
			
			System.out.println(ex);
		}
	}
}
