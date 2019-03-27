package org.softuni.handy.domain.models.service;

import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOrder;

public class ReviewServiceModel extends BaseServiceModel {

    private ServiceOrder serviceOrder;

    private ProfessionalService recipient;

    private String content;

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ProfessionalService getRecipient() {
        return recipient;
    }

    public void setRecipient(ProfessionalService recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
