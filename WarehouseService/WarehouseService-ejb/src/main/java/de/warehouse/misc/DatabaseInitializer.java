package de.warehouse.misc;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.warehouse.persistence.Article;
import de.warehouse.persistence.Customer;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.Role;
import de.warehouse.persistence.StorageLocation;

/**
 * Creates sample data for testing purposes.
 * Hint: You need to set ' <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>' 
 * otherwise the creation will fail due to duplicate keys.
 * @author David
 */
@Singleton
@Startup
public class DatabaseInitializer {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void createTestData() throws Exception {
		Employee e1 = new Employee();

		e1.setFirstName("Max");
		e1.setLastName("Mustermann");
		e1.setMailAddress("dm070491@fh-muenster.de");
		e1.setPassword("geheim");
		e1.setRole(Role.Kommissionierer);

		Employee e2 = new Employee();

		e2.setFirstName("Peter");
		e2.setLastName("Petersen");
		e2.setMailAddress("dm070491@fh-muenster.de");
		e2.setPassword("geheim");
		e2.setRole(Role.Lagerist);
		
		Employee e3 = new Employee();

		e3.setFirstName("Fiete");
		e3.setLastName("Fietersen");
		e3.setMailAddress("dm070491@fh-muenster.de");
		e3.setPassword("geheim");
		e3.setRole(Role.Administrator);
		
		Employee e4 = new Employee();

		e4.setFirstName("Jon");
		e4.setLastName("Doe");
		e4.setMailAddress("dm070491@fh-muenster.de");
		e4.setPassword("geheim");
		e4.setRole(Role.Administrator);

		this.em.persist(e1);
		this.em.persist(e2);
		this.em.persist(e3);
		this.em.persist(e4);
		
		StorageLocation sl1 = new StorageLocation("AA-01-01");
		StorageLocation sl2 = new StorageLocation("AA-01-02");
		StorageLocation sl3 = new StorageLocation("AA-01-03");
		StorageLocation sl4 = new StorageLocation("AA-01-04");
		StorageLocation sl5 = new StorageLocation("AA-01-05");
		
		this.em.persist(sl1);
		this.em.persist(sl2);
		this.em.persist(sl3);
		this.em.persist(sl4);
		this.em.persist(sl5);
		
		Article a1 = new Article("A2000", "Fahrrad TYP-A 15");
		a1.setQuantityOnStock(100);
		a1.setQuantityOnCommitment(0);
		a1.setStorageLocation(sl1);
		
		Article a2 = new Article("A2020","Fahrrad TYP-A 20");
		a2.setQuantityOnStock(25);
		a2.setQuantityOnCommitment(0);
		a2.setStorageLocation(sl2);
		
		Article a3 = new Article("F2010","Fahrrad TYP-F 10");
		a3.setQuantityOnStock(30);
		a3.setQuantityOnCommitment(0);
		a3.setStorageLocation(sl3);
		
		Article a4 = new Article("Z2100","Fahrrad TYP-Z 100");
		a4.setQuantityOnStock(85);
		a4.setQuantityOnCommitment(0);
		a4.setStorageLocation(sl4);
		
		Article a5 = new Article("Z2200","Fahrrad TYP-Z 200");
		a5.setQuantityOnStock(85);
		a5.setQuantityOnCommitment(0);
		a5.setStorageLocation(sl4);
		
		this.em.persist(a1);
		this.em.persist(a2);
		this.em.persist(a3);
		this.em.persist(a4);
		this.em.persist(a5);
		
		Customer c1 = new Customer(3000);
		c1.setDeliveryToleranceInDays(3);
		c1.setName1("Musterfirma GmbH");
		c1.setMailAddress("dm070491@fh-muenster.de");
		
		this.em.persist(c1);
		
		CustomerOrder co1 = new CustomerOrder(new Date(), new Date(2016 - 1900, 8, 10));
		co1.setCommissionProgress(0);
		co1.setEmailSent(false);
		co1.setCustomer(c1);
		co1.setPicker(e1);

		CustomerOrder co2 = new CustomerOrder(new Date(), new Date(2016 - 1900, 7, 31));
		co2.setCommissionProgress(0);
		co2.setEmailSent(false);		
		co2.setCustomer(c1);
		
		CustomerOrder co3 = new CustomerOrder(new Date(), new Date(2016 - 1900, 9, 1));
		co3.setCommissionProgress(0);
		co3.setEmailSent(false);		
		co3.setCustomer(c1);
		
		this.em.persist(co1);
		this.em.persist(co2);
		this.em.persist(co3);
		
		CustomerOrderPosition co1Pos1 = new CustomerOrderPosition();
		co1Pos1.setOrder(co1);
		co1Pos1.setOrderedQuantity(10);
		co1Pos1.setPickedQuantity(0);
		co1Pos1.setArticle(a1);
		
		CustomerOrderPosition co1Pos2 = new CustomerOrderPosition();
		co1Pos2.setOrder(co1);
		co1Pos2.setOrderedQuantity(5);
		co1Pos2.setPickedQuantity(0);
		co1Pos2.setArticle(a2);
		
		CustomerOrderPosition co1Pos3 = new CustomerOrderPosition();
		co1Pos3.setOrder(co1);
		co1Pos3.setOrderedQuantity(25);
		co1Pos3.setPickedQuantity(0);
		co1Pos3.setArticle(a3);
		
		this.em.persist(co1Pos1);
		this.em.persist(co1Pos2);
		this.em.persist(co1Pos3);
	}
}
