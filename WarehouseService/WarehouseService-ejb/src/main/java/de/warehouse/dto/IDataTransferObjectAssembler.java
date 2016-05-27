package de.warehouse.dto;

import de.warehouse.shared.*;

public interface IDataTransferObjectAssembler {
	public ArticleTO mapEntity(Article article);
	public CommissionTO mapEntity(CustomerOrder customerOrder);
	public CommissionPositionTO mapEntity(CustomerOrderPosition customerOrderPosition);
}
