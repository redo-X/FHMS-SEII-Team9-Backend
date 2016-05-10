package de.warehouse.client;

import java.math.BigDecimal;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import de.warehouse.shared.Employee;
import de.warehouse.shared.interfaces.IEmployeeRepository;



/**
 * @author Thoene
 * Diese Klasse realisiert einen rudimentaeren Client zum Zugriff auf das OnlineBankingSystem.
 */
public class SimpleOnlineBankingClient {

	//Testdaten:
	private static final int JOES_KONTO	=1;
	private static final int EMMAS_KONTO=2;
	
	private static IEmployeeRepository remoteSystem;
	
	/**
	 * In dieser Main-Methode werden Requests an den Onlinebanking-Server abgeschickt. Sie koennen die durchgefuehrten
	 * Szenarien nach Belieben anpassen.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
           Context context = new InitialContext();
	       
	       //Lookup-String für eine EJB besteht aus: Name_EA/Name_EJB-Modul/Name_EJB-Klasse!Name_RemoteInterface
	       String lookupString = "WarehouseService-ear/WarehouseService-ejb-1.0.0/EmployeeRepository!de.warehouse.shared.interfaces.IEmployeeRepository";
	       remoteSystem = (IEmployeeRepository) context.lookup(lookupString);
 	       
 	       //Zeige, welche Referenz auf das Server-Objekt der Client erhalten hast:
 	       System.out.println("Client hat folgendes Server-Objekt nach dem Lookup erhalten:");
 	       System.out.println(remoteSystem.toString());
 	       System.out.println();
 	       
 	       Employee e1 = new Employee();
 	       
 	       e1.setFirstName("Max");
 	       e1.setLastName("Mustermann");
 	       
 	       Employee e2 = remoteSystem.Create(e1);
		   
 	       System.out.println(e2.getCode());
 	       System.out.println();
		}
		catch (Exception ex) {
		   	System.out.println(ex);
		}
	}
}
