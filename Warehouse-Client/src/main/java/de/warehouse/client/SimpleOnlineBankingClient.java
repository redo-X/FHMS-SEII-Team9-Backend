package de.warehouse.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import de.warehouse.shared.Employee;
import de.warehouse.shared.Role;
import de.warehouse.shared.interfaces.IEmployeeRepository;



/**
 * @author Thoene
 * Diese Klasse realisiert einen rudimentaeren Client zum Zugriff auf das OnlineBankingSystem.
 */
public class SimpleOnlineBankingClient {
	
	private static IEmployeeRepository remoteSystem;
	
	/**
	 * In dieser Main-Methode werden Requests an den Onlinebanking-Server abgeschickt. Sie koennen die durchgefuehrten
	 * Szenarien nach Belieben anpassen.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
           Context context = new InitialContext();
	       
	       String lookupString = "WarehouseService-ear/WarehouseService-ejb-1.0.0/EmployeeRepository!de.warehouse.shared.interfaces.IEmployeeRepository";
	       remoteSystem = (IEmployeeRepository) context.lookup(lookupString);

 	       System.out.println("Client hat folgendes Server-Objekt nach dem Lookup erhalten:");
 	       System.out.println(remoteSystem.toString());
 	       System.out.println();
 	       
 	       //Employee e3 = remoteSystem.GetByCode(Integer.valueOf(2));
 	       
 	       Employee e1 = new Employee();
 	       
 	       e1.setFirstName("Max");
 	       e1.setLastName("Mustermann");
 	       e1.setRole(Role.Administrator);
 	       
 	       Employee e2 = remoteSystem.Create(e1);
		   
 	       System.out.println(e2.getCode());
 	       
 	       e1 = remoteSystem.GetByCode(e2.getCode());
 	       
 	       e1.setFirstName("Manuel");
 	       
 	       remoteSystem.Update(e1);
 	       
 	       e1 = remoteSystem.GetByCode(e2.getCode());
 	       System.out.println(e1.getFirstName());
		}
		catch (Exception ex) {
		   	System.out.println(ex);
		}
	}
}
