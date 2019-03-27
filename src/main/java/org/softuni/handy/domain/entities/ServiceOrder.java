package org.softuni.handy.domain.entities;

import org.softuni.handy.domain.enums.OrderStatus;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "service_orders")
public class ServiceOrder extends CustomerAction {

    private String address;

    private String problemDescription;

    private ProfessionalService professionalService;

    private OrderStatus orderStatus;

    private LocalDate scheduledDate;

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "problem_description", nullable = false)
    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
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
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "scheduled_date", nullable = false)
    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
