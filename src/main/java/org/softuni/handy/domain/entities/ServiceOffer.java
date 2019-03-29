package org.softuni.handy.domain.entities;


import org.softuni.handy.domain.enums.OfferStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "service_offers")
public class ServiceOffer extends BaseEntity{

    private Integer hours;

    private BigDecimal price;

    private ServiceOrder serviceOrder;

    private ProfessionalService professionalService;

    private OfferStatus offerStatus;

    @Column(name = "hours")
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @ManyToOne
    @JoinColumn(name = "service_id")
    public ProfessionalService getProfessionalService() {
        return professionalService;
    }

    public void setProfessionalService(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }
}
