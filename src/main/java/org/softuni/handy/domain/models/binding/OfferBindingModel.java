package org.softuni.handy.domain.models.binding;

import org.softuni.handy.domain.entities.ServiceOrder;

import java.math.BigDecimal;

public class OfferBindingModel {

    private Integer hours;

    private BigDecimal price;

    private ServiceOrder serviceOrder;

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }


}
