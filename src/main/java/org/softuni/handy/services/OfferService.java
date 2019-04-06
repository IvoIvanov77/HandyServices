package org.softuni.handy.services;

import org.softuni.handy.domain.models.binding.AcceptOfferBindingModel;
import org.softuni.handy.domain.models.service.OfferServiceModel;

import java.util.List;

public interface OfferService {

    boolean createOffer(OfferServiceModel serviceModel, String username);

    List<OfferServiceModel> getAllByOrder(String orderId);

    boolean acceptOffer(AcceptOfferBindingModel bindingModel);
}
