package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOffer;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.models.service.OfferServiceModel;
import org.softuni.handy.repositories.OfferRepository;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private final ProfessionalServiceRepository professionalServiceRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            ProfessionalServiceRepository professionalServiceRepository,
                            ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.professionalServiceRepository = professionalServiceRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean createOffer(OfferServiceModel serviceModel, String username) {
        ServiceOrder serviceOrder = serviceModel.getServiceOrder();
        ProfessionalService professionalService = this.professionalServiceRepository
                .getFirstByUserUsernameAndLocationAndServiceType(
                        username,
                        serviceOrder.getLocation(),
                        serviceOrder.getServiceType())
                .orElse(null);
        serviceModel.setProfessionalService(professionalService);
        ServiceOffer serviceOffer = this.modelMapper.map(serviceModel, ServiceOffer.class);
        try{
            this.offerRepository.save(serviceOffer);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<OfferServiceModel> getAllByOrder(String orderId){
        return this.offerRepository
                .findAllByServiceOrderIdAndAcceptedOrderByPrice(orderId, false)
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferServiceModel.class))
                .collect(Collectors.toList());
    }
}
