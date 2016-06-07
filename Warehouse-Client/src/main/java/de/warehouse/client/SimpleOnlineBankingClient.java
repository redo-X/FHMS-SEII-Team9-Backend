package de.warehouse.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import de.warehouse.shared.CustomerOrder;
import de.warehouse.shared.CustomerOrderPosition;
import de.warehouse.shared.Employee;
import de.warehouse.shared.Role;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.ICommissionService;
import de.warehouse.shared.interfaces.IEmployeeRepository;
import de.warehouse.shared.interfaces.IMailService;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * @author Thoene Diese Klasse realisiert einen rudimentaeren Client zum Zugriff
 *         auf das OnlineBankingSystem.
 */
public class SimpleOnlineBankingClient {

	private static IEmployeeRepository employeeRepository;
	private static ISessionManagement sessionManagement;
	private static ICommissionService commissionService;

	public static void main(String[] args) {
		try {

			Context context = new InitialContext();

			String employeeRepositoryLookupString = "/WarehouseService-ear/WarehouseService-ejb-1.0.0/EmployeeRepository!de.warehouse.shared.interfaces.IEmployeeRepository";
			String sessionManagementLookupString = "/WarehouseService-ear/WarehouseService-ejb-1.0.0/SessionManagement!de.warehouse.shared.interfaces.ISessionManagement";
			String commissionServiceLookupString = "/WarehouseService-ear/WarehouseService-ejb-1.0.0/CommissionService!de.warehouse.shared.interfaces.ICommissionService";

			employeeRepository = (IEmployeeRepository) context.lookup(employeeRepositoryLookupString);
			sessionManagement = (ISessionManagement) context.lookup(sessionManagementLookupString);
			commissionService = (ICommissionService) context.lookup(commissionServiceLookupString);

			int sessionId = sessionManagement.createSession(1, "geheim");

			try {
				List<CustomerOrder> customerOrders = commissionService.getIncompletedCustomerOrders();
				for (CustomerOrder c : customerOrders) {
					System.out.println(c.toString());
				}
				
				CustomerOrder customerOrder = commissionService.getCustomerOrderById(4);
				System.out.println(customerOrder.toString());
				
				commissionService.updateStart(customerOrder.getCode());				
				try {					
					commissionService.allocateCustomerOrder(customerOrder.getCode(), 1);
				}catch(CustomerOrderAlreadyAllocatedException e) {
					System.out.println(e.getMessage());
				}
				
				List<CustomerOrderPosition> customerOrderPositions = commissionService.getPositionByCustomerOrderId(customerOrder.getCode());
				for(CustomerOrderPosition p : customerOrderPositions) {
					System.out.println(p.toString());
					
					commissionService.updatePickedQuantity(p.getCustomerOrderPositionId(), p.getRemainingQuantity());					
				}				
				commissionService.updateFinish(customerOrder.getCode());	
				
				customerOrders = commissionService.getIncompletedCustomerOrders();
				for (CustomerOrder c : customerOrders) {
					System.out.println(c.toString());
				}
			} finally {
				sessionManagement.closeSession(sessionId);
			}

		} catch (Exception ex) {

			System.out.println(ex);
		}
	}
}
