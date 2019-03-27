package org.softuni.handy.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    private ServiceOrder serviceOrder;

    private ProfessionalService recipient;

    private String content;

    @ManyToOne
    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @ManyToOne
    public ProfessionalService getRecipient() {
        return recipient;
    }

    public void setRecipient(ProfessionalService recipient) {
        this.recipient = recipient;
    }

    @Column(name = "content", nullable = false, columnDefinition="text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
