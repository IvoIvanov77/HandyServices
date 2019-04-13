package org.softuni.handy.services;

import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOffer;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.binding.AcceptOfferBindingModel;
import org.softuni.handy.domain.models.service.OfferServiceModel;
import org.softuni.handy.exception.InvalidServiceModelException;
import org.softuni.handy.exception.ResourceNotFoundException;
import org.softuni.handy.repositories.OfferRepository;
import org.softuni.handy.repositories.OrderRepository;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.softuni.handy.services.constants.ErrorMessages;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class OfferServiceImpl extends BaseService implements OfferService {

    private final OfferRepository offerRepository;

    private final ProfessionalServiceRepository professionalServiceRepository;

    private final OrderRepository orderRepository;

    private final DtoMapper mapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            ProfessionalServiceRepository professionalServiceRepository,
                            OrderRepository orderRepository, DtoMapper mapper, Validator validator) {
        super(validator);
        this.offerRepository = offerRepository;
        this.professionalServiceRepository = professionalServiceRepository;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }


    @Override
    public boolean createOffer(OfferServiceModel serviceModel, String username) {
        if (hasErrors(serviceModel)){
            throw new InvalidServiceModelException(ErrorMessages.INVALID_OFFER_MODEL);
        }
        ServiceOrder serviceOrder = serviceModel.getServiceOrder();
        ProfessionalService professionalService = this.professionalServiceRepository
                .getFirstByUserUsernameAndLocationAndServiceType(
                        username,
                        serviceOrder.getLocation(),
                        serviceOrder.getServiceType())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.PROFESSIONAL_SERVICE_NOT_FOUND_ERROR_MESSAGE));
        serviceModel.setProfessionalService(professionalService);
        ServiceOffer serviceOffer = this.mapper.map(serviceModel, ServiceOffer.class);
        
        if(serviceOrder.getOffers()
                .stream()
                .anyMatch(offer -> offer.getProfessionalService()
                        .equals(professionalService))){
            return false;
        }
        try{
            this.orderRepository.updateOrderStatus(OrderStatus.OFFERED, serviceOrder.getId());
            this.offerRepository.saveAndFlush(serviceOffer);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<OfferServiceModel> getAllByOrder(String orderId){
        List<ServiceOffer> offers = this.offerRepository
                .findAllByServiceOrderIdAndAcceptedOrderByPrice(orderId, false);
        return this.mapper.map(offers, OfferServiceModel.class)
                .collect(Collectors.toList());
    }

    @Override
    public boolean acceptOffer(AcceptOfferBindingModel bindingModel){
        ProfessionalService professionalService =
                this.professionalServiceRepository
                        .findById(bindingModel.getProfessionalServiceId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(ErrorMessages.PROFESSIONAL_SERVICE_NOT_FOUND_ERROR_MESSAGE));
        try{
            this.offerRepository.acceptOffer(bindingModel.getOfferId());
            this.orderRepository.acceptOrder(bindingModel.getServiceOrderId(),
                    professionalService);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
