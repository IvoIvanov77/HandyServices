package org.softuni.handy.domain.models.binding;

import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.models.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OfferBindingModel {

    private Integer hours;

    private BigDecimal price;

    private ServiceOrder serviceOrder;

    @Min(value = ValidationConstraints.MIN_OFFER_HOURS, message =
            ValidationMessages.INVALID_HOURS_ERROR_MESSAGE)
    @NotNull(message = ValidationMessages.HOURS_NOT_NULL_ERROR_MESSAGE)
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @DecimalMin(value = ValidationConstraints.MIN_OFFER_PRICE,
            message = ValidationMessages.INVALID_PRICE_ERROR_MESSAGE)
    @NotNull(message = ValidationMessages.PRICE_NOT_NULL_ERROR_MESSAGE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = ValidationMessages.ORDER_NOT_NULL_ERROR_MESSAGE)
    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }


}
