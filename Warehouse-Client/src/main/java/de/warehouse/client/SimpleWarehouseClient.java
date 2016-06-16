package de.warehouse.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.interfaces.ICommissionService;
import de.warehouse.shared.interfaces.ISessionManagement;


public class SimpleWarehouseClient {

	private static ISessionManagement sessionManagement;
	private static ICommissionService commissionService;

	public static void main(String[] args) {
		try {

			Context context = new InitialContext();

			String sessionManagementLookupString = "/WarehouseService-ear/WarehouseService-ejb-1.0.0/SessionManagement!de.warehouse.shared.interfaces.ISessionManagement";
			String commissionServiceLookupString = "/WarehouseService-ear/WarehouseService-ejb-1.0.0/CommissionService!de.warehouse.shared.interfaces.ICommissionService";

			sessionManagement = (ISessionManagement) context.lookup(sessionManagementLookupString);
			commissionService = (ICommissionService) context.lookup(commissionServiceLookupString);

			int sessionId = sessionManagement.createSession(1, "geheim");

			try {
				List<CustomerOrder> customerOrders = commissionService.getPendingCustomerOrdersWithoutPicker();
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
				
				List<CustomerOrderPosition> customerOrderPositions = commissionService.getPositionsByCustomerOrderId(customerOrder.getCode());
				for(CustomerOrderPosition p : customerOrderPositions) {
					System.out.println(p.toString());
					
					commissionService.updatePickedQuantity(p.getCustomerOrderPositionId(), p.getRemainingQuantity());					
				}				
				commissionService.updateFinish(customerOrder.getCode());	
				
				customerOrders = commissionService.getPendingCustomerOrdersWithoutPicker();
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
