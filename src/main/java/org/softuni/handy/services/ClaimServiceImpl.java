package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.Claim;
import org.softuni.handy.domain.models.service.ClaimServiceModel;
import org.softuni.handy.domain.models.service.CreateClaimServiceModel;
import org.softuni.handy.repositories.ClaimRepository;
import org.softuni.handy.repositories.OrderRepository;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    private final OrderRepository orderRepository;

    private final ProfessionalServiceRepository serviceRepository;

    private final ModelMapper modelMapper;


    public ClaimServiceImpl(ClaimRepository claimRepository, OrderRepository orderRepository,
                            ProfessionalServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.claimRepository = claimRepository;
        this.orderRepository = orderRepository;
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }

    public boolean openClaim(CreateClaimServiceModel serviceModel){
        Claim claim = this.modelMapper.map(serviceModel, Claim.class);
        try{
            claim.setProfessionalService(this.serviceRepository.getOne(
                    serviceModel.getProfessionalServiceId()));
            claim.setServiceOrder(this.orderRepository.getOne(
                    serviceModel.getServiceOrderId()));
            this.claimRepository.save(claim);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<ClaimServiceModel> getUserClaims(String username, boolean isClosed, boolean isProfService){
        if(isProfService){
            return this.claimRepository
                    .findAllByClosedAndProfessionalService_User_Username(isClosed, username)
                    .stream()
                    .map(claim -> this.modelMapper.map(claim, ClaimServiceModel.class))
                    .collect(Collectors.toList());
        }
        return this.claimRepository
                .findAllByClosedAndServiceOrder_User_Username(isClosed, username)
                .stream()
                .map(claim -> this.modelMapper.map(claim, ClaimServiceModel.class))
                .collect(Collectors.toList());
    }

    public boolean closeClaim(String claimId){
        Claim claim = this.claimRepository.findById(claimId).orElse(null);
        if(claim == null){
            return false;
        }
        claim.setClosed(true);
        this.claimRepository.save(claim);
        return true;
    }
}
