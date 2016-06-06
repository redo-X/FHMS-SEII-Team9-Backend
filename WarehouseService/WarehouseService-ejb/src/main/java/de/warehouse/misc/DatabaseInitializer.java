package de.warehouse.misc;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.warehouse.shared.Article;
import de.warehouse.shared.Customer;
import de.warehouse.shared.CustomerOrder;
import de.warehouse.shared.CustomerOrderPosition;
import de.warehouse.shared.Employee;
import de.warehouse.shared.Role;
import de.warehouse.shared.StorageLocation;
import de.warehouse.shared.utils.DateUtil;

@Singleton
@Startup
public class DatabaseInitializer {

	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	public void createTestData() {
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

		this.em.persist(e1);
		this.em.persist(e2);
		this.em.persist(e3);
		
		StorageLocation sl1 = new StorageLocation();
		sl1.setCode("AA-01-01");		
		StorageLocation sl2 = new StorageLocation();
		sl2.setCode("AA-01-02");		
		StorageLocation sl3 = new StorageLocation();
		sl3.setCode("AA-01-03");		
		StorageLocation sl4 = new StorageLocation();
		sl4.setCode("AA-01-04");		
		StorageLocation sl5 = new StorageLocation();
		sl5.setCode("AA-01-05");
		
		this.em.persist(sl1);
		this.em.persist(sl2);
		this.em.persist(sl3);
		this.em.persist(sl4);
		this.em.persist(sl5);
		
		Article a1 = new Article();
		a1.setCode("A2000");
		a1.setName("Fahrrad TYP-A 15");
		a1.setQuantityOnStock(100);
		a1.setQuantityOnCommitment(0);
		a1.setStorageLocation(sl1);
		
		Article a2 = new Article();
		a2.setCode("A2020");
		a2.setName("Fahrrad TYP-A 20");
		a2.setQuantityOnStock(25);
		a2.setQuantityOnCommitment(0);
		a2.setStorageLocation(sl2);
		
		Article a3 = new Article();
		a3.setCode("F2010");
		a3.setName("Fahrrad TYP-F 10");
		a3.setQuantityOnStock(30);
		a3.setQuantityOnCommitment(0);
		a3.setStorageLocation(sl3);
		
		Article a4 = new Article();
		a4.setCode("Z2100");
		a4.setName("Fahrrad TYP-Z 100");
		a4.setQuantityOnStock(85);
		a4.setQuantityOnCommitment(0);
		a4.setStorageLocation(sl4);
		
		this.em.persist(a1);
		this.em.persist(a2);
		this.em.persist(a3);
		this.em.persist(a4);
		
		Customer c1 = new Customer();
		c1.setCode(3000);
		c1.setDeliveryToleranceInDays(3);
		c1.setName1("Musterfirma GmbH");
		c1.setMailAddress("info@musterfirma.de");
		
		this.em.persist(c1);
		
		CustomerOrder co1 = new CustomerOrder();
		co1.setCommissionProgress(0);
		co1.setDueDate(new Date(2016 - 1900, 8, 10));
		co1.setEmailSent(false);
		co1.setOrderDate(new Date());
		co1.setCustomer(c1);
		co1.setPicker(e1);
		
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
		
		
		
		CustomerOrder co2 = new CustomerOrder();
		co2.setCommissionProgress(0);
		co2.setDueDate(new Date(2016 - 1900, 7, 31));
		co2.setEmailSent(false);
		co2.setOrderDate(new Date());
		co2.setCustomer(c1);
		
		CustomerOrder co3 = new CustomerOrder();
		co3.setCommissionProgress(0);
		co3.setDueDate(new Date(2016 - 1900, 9, 1));
		co3.setEmailSent(false);
		co3.setOrderDate(new Date());
		co3.setCustomer(c1);
		
		this.em.persist(co1);
		this.em.persist(co1Pos1);
		this.em.persist(co1Pos2);
		this.em.persist(co1Pos3);
		
		this.em.persist(co2);
		this.em.persist(co3);
		
		
	}
}
