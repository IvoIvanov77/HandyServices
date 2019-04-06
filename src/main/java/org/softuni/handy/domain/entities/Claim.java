package org.softuni.handy.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "claims")
public class Claim extends  BaseEntity{

    private String reason;

    private ServiceOrder serviceOrder;

    private ProfessionalService professionalService;

    private boolean isClosed;

    @Column(name = "reason", nullable = false,  columnDefinition="text")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    @Column(name = "closed", nullable = false)
    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
