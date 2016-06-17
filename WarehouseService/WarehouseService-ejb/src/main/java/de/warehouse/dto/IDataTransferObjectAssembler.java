package de.warehouse.dto;

import de.warehouse.persistence.Article;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;

public interface IDataTransferObjectAssembler {
	public ArticleTO mapEntity(Article article);
	public ArticleTO[] mapEntities(Article[] articles);
	public CommissionTO mapEntity(CustomerOrder customerOrder);
	public CommissionTO[] mapEntities(CustomerOrder[] customerOrders);
	public CommissionPositionTO mapEntity(CustomerOrderPosition customerOrderPosition);
	public CommissionPositionTO[] mapEntities(CustomerOrderPosition[] customerOrderPositions);
}
