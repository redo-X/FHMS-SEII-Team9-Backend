package de.warehouse.integration;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import de.warehouse.dto.IDataTransferObjectAssembler;
import de.warehouse.dto.responses.article.UpdateQuantityOnStockOfArticleResponse;
import de.warehouse.dto.responses.article.UpdateStorageLocationOfArticleResponse;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.interfaces.IArticleRepository;

@WebService
@Stateless
public class ArticleManagementIntegration {
	@EJB
	private IDataTransferObjectAssembler dtoAssembler;
	
	@EJB
	private IArticleRepository articleRepository;
	
	/**
	 * Updates the storage location of an article.
	 * @param articleCode Identifier of the article
	 * @param storageLocationCode Identifier of the storage location
	 * @return Response that holds the status of the operation
	 */
	public UpdateStorageLocationOfArticleResponse updateStorageLocationOfArticle( String articleCode, String storageLocationCode) {
		UpdateStorageLocationOfArticleResponse response = new UpdateStorageLocationOfArticleResponse();
		
		try {
			this.articleRepository.updateStorageLocationOfArticle(articleCode, storageLocationCode);
		} catch (EntityNotFoundException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		}
		
		return response;
	}
	/**
	 * Updates the stocked quantity of an article.
	 * @param articleCode Identifier of the article
	 * @param receiptQuantity The receipt quantity of the article.
	 * @return Response that holds the status of the operation
	 */
	public UpdateQuantityOnStockOfArticleResponse updateQuantityOnStockOfArticle(String articleCode, int receiptQuantity) {
		UpdateQuantityOnStockOfArticleResponse response = new UpdateQuantityOnStockOfArticleResponse();
		
		try {
			this.articleRepository.updateQuantityOnStockOfArticle(articleCode, receiptQuantity);
		} catch (EntityNotFoundException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		}
		
		return response;
	}
}