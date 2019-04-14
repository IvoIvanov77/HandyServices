package org.softuni.handy.services;

import org.softuni.handy.domain.models.service.ClaimServiceModel;
import org.softuni.handy.domain.models.service.CreateClaimServiceModel;

import java.util.List;

public interface ClaimService {
    boolean openClaim(CreateClaimServiceModel serviceModel);

    List<ClaimServiceModel> getUserClaims(String username, boolean isClosed, boolean isProfService);

    boolean closeClaim(String claimId);

    ClaimServiceModel getOpenedOrderClaim(String orderId);
}
