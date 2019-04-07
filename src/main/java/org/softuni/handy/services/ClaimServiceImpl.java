package org.softuni.handy.services;

import org.softuni.handy.domain.entities.Claim;
import org.softuni.handy.domain.models.service.ClaimServiceModel;
import org.softuni.handy.domain.models.service.CreateClaimServiceModel;
import org.softuni.handy.repositories.ClaimRepository;
import org.softuni.handy.repositories.OrderRepository;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.softuni.handy.util.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    private final OrderRepository orderRepository;

    private final ProfessionalServiceRepository serviceRepository;

    private final DtoMapper mapper;

    public ClaimServiceImpl(ClaimRepository claimRepository, OrderRepository orderRepository,
                            ProfessionalServiceRepository serviceRepository, DtoMapper mapper) {
        this.claimRepository = claimRepository;
        this.orderRepository = orderRepository;
        this.serviceRepository = serviceRepository;
        this.mapper = mapper;
    }


    @Override
    public boolean openClaim(CreateClaimServiceModel serviceModel) {
        Claim claim = this.mapper.map(serviceModel, Claim.class);
        try {
            claim.setProfessionalService(this.serviceRepository.getOne(
                    serviceModel.getProfessionalService()));
            claim.setServiceOrder(this.orderRepository.getOne(
                    serviceModel.getServiceOrder()));
            this.claimRepository.save(claim);
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
        Claim claim = this.claimRepository.findById(claimId).orElse(null);
        if (claim == null) {
            return false;
        }
        claim.setClosed(true);
        this.claimRepository.save(claim);
        return true;
    }

    private List<Claim> findUserClaims(String username, boolean isClosed, boolean isProfService) {
        return isProfService ?
                this.claimRepository
                        .findAllByClosedAndProfessionalService_User_Username(isClosed, username) :
                this.claimRepository
                        .findAllByClosedAndServiceOrder_User_Username(isClosed, username);
    }


}
