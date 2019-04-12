package org.softuni.handy.domain.entities;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "service_offers")
public class ServiceOffer extends BaseEntity{

    private Integer hours;

    private BigDecimal price;

    private ServiceOrder serviceOrder;

    private ProfessionalService professionalService;

    private boolean accepted;

    @Column(name = "hours", nullable = false)
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
    @JoinColumn(name = "order_id", nullable = false)
    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    public ProfessionalService getProfessionalService() {
        return professionalService;
    }

    public void setProfessionalService(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @Column(name = "accepted", nullable = false)
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
