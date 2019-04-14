package org.softuni.handy.services;

import org.softuni.handy.domain.entities.Claim;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.service.ClaimServiceModel;
import org.softuni.handy.domain.models.service.CreateClaimServiceModel;
import org.softuni.handy.exception.InvalidServiceModelException;
import org.softuni.handy.exception.ResourceNotFoundException;
import org.softuni.handy.repositories.ClaimRepository;
import org.softuni.handy.repositories.OrderRepository;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.softuni.handy.services.constants.ErrorMessages;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl extends BaseService implements ClaimService {

    public static final String CAN_NOT_CREATE_ANOTHER_CLAIM = "Can not create another claim";
    private final ClaimRepository claimRepository;

    private final OrderRepository orderRepository;

    private final ProfessionalServiceRepository serviceRepository;

    private final DtoMapper mapper;

    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository, OrderRepository orderRepository,
                            ProfessionalServiceRepository serviceRepository,
                            DtoMapper mapper, Validator validator) {
        super(validator);
        this.claimRepository = claimRepository;
        this.orderRepository = orderRepository;
        this.serviceRepository = serviceRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean openClaim(CreateClaimServiceModel serviceModel) {
        if(hasErrors(serviceModel)){
            throw new InvalidServiceModelException(ErrorMessages.INVALID_CLAIM_MODEL);
        }
        if(this.claimRepository
                .countAllByServiceOrderIdAndClosed(serviceModel.getServiceOrder(), false) > 0){
            throw new UnsupportedOperationException(CAN_NOT_CREATE_ANOTHER_CLAIM);
        }
        Claim claim = this.mapper.map(serviceModel, Claim.class);
        ProfessionalService professionalService = this.serviceRepository
                .findById(serviceModel.getProfessionalService())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.PROFESSIONAL_SERVICE_NOT_FOUND_ERROR_MESSAGE));
        ServiceOrder serviceOrder = this.orderRepository
                .findById(serviceModel.getServiceOrder())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ORDER_NOT_FOUND_ERROR_MESSAGE));
        claim.setProfessionalService(professionalService);
        claim.setServiceOrder(serviceOrder);
        try {
            this.claimRepository.save(claim);
            this.orderRepository.updateOrderStatus(OrderStatus.CLAIMED, serviceModel.getServiceOrder());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ClaimServiceModel> getUserClaims(String username, boolean isClosed, boolean isProfService) {
        List<Claim> claims = this.findUserClaims(username, isClosed, isProfService);
        return this.mapper.map(claims, ClaimServiceModel.class)
                .collect(Collectors.toList());
    }

    @Override
    public boolean closeClaim(String claimId) {
        Claim claim = this.claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CLAIM_NOT_FOUND));
        claim.setClosed(true);
        try {
            this.claimRepository.save(claim);
            this.orderRepository.updateOrderStatus(OrderStatus.COMPLETED, claim.getServiceOrder().getId());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private List<Claim> findUserClaims(String username, boolean isClosed, boolean isProfService) {
        return isProfService ?
                this.claimRepository
                        .findAllByClosedAndProfessionalService_User_Username(isClosed, username) :
                this.claimRepository
                        .findAllByClosedAndServiceOrder_User_Username(isClosed, username);
    }

    @Override
    public ClaimServiceModel getOpenedOrderClaim(String orderId){
        Claim claim = this.claimRepository
                .findFirstByClosedAndServiceOrderId(false, orderId)
                .orElseThrow(() -> new ResourceNotFoundException(""));
        return this.mapper.map(claim, ClaimServiceModel.class);
    }


}
