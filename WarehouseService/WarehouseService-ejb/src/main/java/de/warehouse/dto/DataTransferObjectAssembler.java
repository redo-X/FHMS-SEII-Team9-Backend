package de.warehouse.dto;

import javax.ejb.Local;
import javax.ejb.Stateless;

import de.warehouse.shared.Article;
import de.warehouse.shared.CustomerOrder;
import de.warehouse.shared.CustomerOrderPosition;
import de.warehouse.shared.Employee;

/**
 * This stateless session bean provides mapping methods to translate
 * between entities and data transfer objects.
 * @author David
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
	public CommissionTO mapEntity(CustomerOrder customerOrder) {
		Employee picker = customerOrder.getPicker();
		
		return new CommissionTO(
				customerOrder.getCode(),
				customerOrder.getPositions().size(),
				picker != null,
				customerOrder.getProgress(),
				picker == null ? -1 : picker.getCode(),
				picker == null ? "" : picker.getFullName());
	}

	@Override
	public CommissionPositionTO mapEntity(CustomerOrderPosition customerOrderPosition) {
		return new CommissionPositionTO(
				customerOrderPosition.getCustomerOrderId(),
				customerOrderPosition.getArticle().getCode(),
				customerOrderPosition.getArticle().getName(),
				customerOrderPosition.getArticle().getStorageLocation().getCode(),
				customerOrderPosition.getRemainingQuantity(),
				customerOrderPosition.getArticle().getQuantityOnStock());
	}

	

}
