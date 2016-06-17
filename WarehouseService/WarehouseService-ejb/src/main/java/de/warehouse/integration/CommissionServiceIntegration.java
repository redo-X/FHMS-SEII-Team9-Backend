package de.warehouse.integration;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import de.warehouse.dto.IDataTransferObjectAssembler;
import de.warehouse.dto.responses.commission.AllocateCommissionResponse;
import de.warehouse.dto.responses.commission.CommitCommissionMessageResponse;
import de.warehouse.dto.responses.commission.GetCommissionPositionsResponse;
import de.warehouse.dto.responses.commission.GetCommissionsResponse;
import de.warehouse.dto.responses.commission.UpdateFinishOfCommissionResponse;
import de.warehouse.dto.responses.commission.UpdatePickedQuantityResponse;
import de.warehouse.dto.responses.commission.UpdateStartOfCommissionResponse;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.CustomerOrderNotCompletelyCommissioned;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;
import de.warehouse.shared.interfaces.ICommissionMessages;
import de.warehouse.shared.interfaces.ICommissionService;

@WebService
@Stateless
public class CommissionServiceIntegration {

	@EJB
	private ICommissionService commissionService;
	
	@EJB
	private ICommissionMessages commissionMessageService;
	
	@EJB
	private IDataTransferObjectAssembler dtoAssembler;
	

	public AllocateCommissionResponse allocateCommission(int customerOrderId, int employeeId) {
		AllocateCommissionResponse response = new AllocateCommissionResponse();
		try {
			this.commissionService.allocateCustomerOrder(customerOrderId, employeeId);
		} catch (CustomerOrderAlreadyAllocatedException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (EntityNotFoundException e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public UpdatePickedQuantityResponse updatePickedQuantity(int customerOrderPositionId, int pickedQuantity) {
		UpdatePickedQuantityResponse response = new UpdatePickedQuantityResponse();

		try {
			this.commissionService.updatePickedQuantity(customerOrderPositionId, pickedQuantity);
		} catch (NegativeQuantityException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (PickedQuantityTooHighException e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderMustBeAllocateToPicker e) {
			response.setResultCode(-3);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public UpdateStartOfCommissionResponse updateStartOfCommission(int commissionId) {
		UpdateStartOfCommissionResponse response = new UpdateStartOfCommissionResponse();

		try {
			this.commissionService.updateStart(commissionId);
		} catch (CustomerOrderCommissionAlreadyStartedException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderMustBeAllocateToPicker e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public UpdateFinishOfCommissionResponse updateFinishOfCommission(int commissionId) {
		UpdateFinishOfCommissionResponse response = new UpdateFinishOfCommissionResponse();

		try {
			this.commissionService.updateFinish(commissionId);
		} catch (CustomerOrderCommissionAlreadyFinishedException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderMustBeAllocateToPicker e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderNotCompletelyCommissioned e) {
			response.setResultCode(-3);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public GetCommissionsResponse getPendingCommissionsWithoutPicker() {
		GetCommissionsResponse response = new GetCommissionsResponse();

		List<CustomerOrder> l = this.commissionService.getPendingCustomerOrdersWithoutPicker();
		
		response.setCommissions(this.dtoAssembler.mapEntities(l.toArray(new CustomerOrder[l.size()])));

		return response;
	}
	public GetCommissionsResponse getPendingCommissionsByPickerId(int pickerId) {
		GetCommissionsResponse response = new GetCommissionsResponse();

		List<CustomerOrder> l = this.commissionService.getPendingCustomerOrdersByPickerId(pickerId);
		
		response.setCommissions(this.dtoAssembler.mapEntities(l.toArray(new CustomerOrder[l.size()])));
	
		return response;
	}

	public GetCommissionPositionsResponse getPendingCommissionPositionsByCommissionId(int commissionId) {
		GetCommissionPositionsResponse response = new GetCommissionPositionsResponse();
		
		List<CustomerOrderPosition> positions = this.commissionService.getPendingPositionsByCustomerOrderId(commissionId);
		
		response.setCommissionPositions(this.dtoAssembler.mapEntities(positions.toArray(new CustomerOrderPosition[positions.size()])));
		
		return response;
	}

	public CommitCommissionMessageResponse commitCommissionMessage(int sessionId, int commissionPositionId, int differenceQuantity, String note) {
		CommitCommissionMessageResponse response = new CommitCommissionMessageResponse();
		
		this.commissionMessageService.commitMessage(sessionId, commissionPositionId, differenceQuantity, note);
		
		return response;
	}
}