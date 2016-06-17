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
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.CustomerOrderNotCompletelyCommissioned;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.ICommissionMessages;
import de.warehouse.shared.interfaces.ICommissionService;

/**
 * @author Florian, David
 */
@WebService
@Stateless
public class CommissionServiceIntegration {

	@EJB
	private ICommissionService commissionService;

	@EJB
	private ICommissionMessages commissionMessageService;

	@EJB
	private IDataTransferObjectAssembler dtoAssembler;

	public AllocateCommissionResponse allocateCommission(int sessionId, int customerOrderId, int employeeId) {
		AllocateCommissionResponse response = new AllocateCommissionResponse();
		try {
			this.commissionService.allocateCustomerOrder(sessionId, customerOrderId, employeeId);
		} catch (CustomerOrderAlreadyAllocatedException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (EntityNotFoundException e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		} catch (SessionExpiredException e) {
			response.setResultCode(SessionExpiredException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		} catch (AccessDeniedException e) {
			response.setResultCode(AccessDeniedException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public UpdatePickedQuantityResponse updatePickedQuantity(int sessionId, int customerOrderPositionId, int pickedQuantity) {
		UpdatePickedQuantityResponse response = new UpdatePickedQuantityResponse();

		try {
			this.commissionService.updatePickedQuantity(sessionId, customerOrderPositionId, pickedQuantity);
		} catch (NegativeQuantityException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (PickedQuantityTooHighException e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderMustBeAllocateToPicker e) {
			response.setResultCode(-3);
			response.setResultMessage(e.getMessage());
		} catch (SessionExpiredException e) {
			response.setResultCode(SessionExpiredException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		} catch (AccessDeniedException e) {
			response.setResultCode(AccessDeniedException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public UpdateStartOfCommissionResponse updateStartOfCommission(int sessionId, int commissionId) {
		UpdateStartOfCommissionResponse response = new UpdateStartOfCommissionResponse();

		try {
			this.commissionService.updateStart(sessionId, commissionId);
		} catch (CustomerOrderCommissionAlreadyStartedException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderMustBeAllocateToPicker e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		} catch (SessionExpiredException e) {
			response.setResultCode(SessionExpiredException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		} catch (AccessDeniedException e) {
			response.setResultCode(AccessDeniedException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public UpdateFinishOfCommissionResponse updateFinishOfCommission(int sessionId, int commissionId) {
		UpdateFinishOfCommissionResponse response = new UpdateFinishOfCommissionResponse();

		try {
			this.commissionService.updateFinish(sessionId, commissionId);
		} catch (CustomerOrderCommissionAlreadyFinishedException e) {
			response.setResultCode(-1);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderMustBeAllocateToPicker e) {
			response.setResultCode(-2);
			response.setResultMessage(e.getMessage());
		} catch (CustomerOrderNotCompletelyCommissioned e) {
			response.setResultCode(-3);
			response.setResultMessage(e.getMessage());
		} catch (SessionExpiredException e) {
			response.setResultCode(SessionExpiredException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		} catch (AccessDeniedException e) {
			response.setResultCode(AccessDeniedException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public GetCommissionsResponse getPendingCommissionsWithoutPicker(int sessionId) {
		GetCommissionsResponse response = new GetCommissionsResponse();

		List<CustomerOrder> l;
		try {
			l = this.commissionService.getPendingCustomerOrdersWithoutPicker(sessionId);

			response.setCommissions(this.dtoAssembler.mapEntities(l.toArray(new CustomerOrder[l.size()])));
		} catch (SessionExpiredException e) {
			response.setResultCode(SessionExpiredException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		} catch (AccessDeniedException e) {
			response.setResultCode(AccessDeniedException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		}
		
		return response;
	}

	public GetCommissionsResponse getPendingCommissionsByPickerId(int sessionId, int pickerId) {
		GetCommissionsResponse response = new GetCommissionsResponse();

		List<CustomerOrder> l;
		try {
			l = this.commissionService.getPendingCustomerOrdersByPickerId(sessionId, pickerId);

			response.setCommissions(this.dtoAssembler.mapEntities(l.toArray(new CustomerOrder[l.size()])));
		} catch (SessionExpiredException e) {
			response.setResultCode(SessionExpiredException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		} catch (AccessDeniedException e) {
			response.setResultCode(AccessDeniedException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		}
		
		return response;
	}

	public GetCommissionPositionsResponse getPendingCommissionPositionsByCommissionId(int sessionId, int commissionId) {
		GetCommissionPositionsResponse response = new GetCommissionPositionsResponse();

		List<CustomerOrderPosition> positions;
		try {
			positions = this.commissionService.getPendingPositionsByCustomerOrderId(sessionId, commissionId);
			
			response.setCommissionPositions(this.dtoAssembler.mapEntities(positions.toArray(new CustomerOrderPosition[positions.size()])));
		} catch (SessionExpiredException e) {
			response.setResultCode(SessionExpiredException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		} catch (AccessDeniedException e) {
			response.setResultCode(AccessDeniedException.ERROR_CODE);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	public CommitCommissionMessageResponse commitCommissionMessage(int sessionId, int commissionPositionId,
			int differenceQuantity, String note) {
		CommitCommissionMessageResponse response = new CommitCommissionMessageResponse();

		this.commissionMessageService.commitMessage(sessionId, commissionPositionId, differenceQuantity, note);

		return response;
	}
}