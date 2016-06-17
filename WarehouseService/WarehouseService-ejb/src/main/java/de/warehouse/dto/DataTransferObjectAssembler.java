package de.warehouse.dto;

import javax.ejb.Local;
import javax.ejb.Stateless;

import de.warehouse.persistence.Article;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.Employee;

/**
 * This stateless session bean provides mapping methods to translate
 * between entities and data transfer objects.
 * @author David, Florian, Thomas, Marco
 */
@Stateless
@Local(IDataTransferObjectAssembler.class)
public class DataTransferObjectAssembler implements IDataTransferObjectAssembler {

	@Override
	public ArticleTO mapEntity(Article article) {
		return new ArticleTO(
				article.getCode(),
				article.getName(),
				article.getQuantityOnStock(),
				article.getStorageLocation().getCode());
	}
	@Override
	public ArticleTO[] mapEntities(Article[] articles) {
		ArticleTO[] result = new ArticleTO[articles.length];
		
		for(int i = 0; i < articles.length;i++) {
			result[i] = this.mapEntity(articles[i]);
		}
		
		return result;
	}

	@Override
	public CommissionTO mapEntity(CustomerOrder customerOrder) {
		Employee picker = customerOrder.getPicker();
		
		return new CommissionTO(
				customerOrder.getCode(),
				customerOrder.getPendingPositionCount(),
				(int)(customerOrder.getOrderDate().getTime() / 1000),
				(int)(customerOrder.getDueDate().getTime() / 1000),
				picker != null,
				customerOrder.getProgress(),
				picker == null ? -1 : picker.getCode(),
				picker == null ? "" : picker.getFullName());
	}

	@Override
	public CommissionTO[] mapEntities(CustomerOrder[] customerOrders) {
		CommissionTO[] result = new CommissionTO[customerOrders.length];
		
		for(int i = 0; i < customerOrders.length;i++) {
			result[i] = this.mapEntity(customerOrders[i]);
		}
		
		return result;
	}

	@Override
	public CommissionPositionTO mapEntity(CustomerOrderPosition customerOrderPosition) {
		return new CommissionPositionTO(
				customerOrderPosition.getCustomerOrderPositionId(),
				customerOrderPosition.getArticle().getCode(),
				customerOrderPosition.getArticle().getName(),
				customerOrderPosition.getArticle().getStorageLocation().getCode(),
				customerOrderPosition.getRemainingQuantity(),
				customerOrderPosition.getArticle().getQuantityOnStock());
	}
	@Override
	public CommissionPositionTO[] mapEntities(CustomerOrderPosition[] customerOrderPositions) {
		CommissionPositionTO[] result = new CommissionPositionTO[customerOrderPositions.length];
		
		for(int i = 0; i < customerOrderPositions.length;i++) {
			result[i] = this.mapEntity(customerOrderPositions[i]);
		}
		
		return result;
	}
	
}
