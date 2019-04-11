package org.softuni.handy.domain.models.view;

public class ClaimListViewModel {

    private String id;

    private String reason;

    private String serviceOrderServiceTypeServiceName;

    private String serviceOrderScheduledDate;

    private String serviceOrderLocationTown;

    private String professionalServiceFirstName;

    private String professionalServiceLastName;

    private String professionalServicePhoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getServiceOrderServiceTypeServiceName() {
        return serviceOrderServiceTypeServiceName;
    }

    public void setServiceOrderServiceTypeServiceName(String serviceOrderServiceTypeServiceName) {
        this.serviceOrderServiceTypeServiceName = serviceOrderServiceTypeServiceName;
    }

    public String getServiceOrderScheduledDate() {
        return serviceOrderScheduledDate;
    }

    public void setServiceOrderScheduledDate(String serviceOrderScheduledDate) {
        this.serviceOrderScheduledDate = serviceOrderScheduledDate;
    }

    public String getServiceOrderLocationTown() {
        return serviceOrderLocationTown;
    }

    public void setServiceOrderLocationTown(String serviceOrderLocationTown) {
        this.serviceOrderLocationTown = serviceOrderLocationTown;
    }

    public String getProfessionalServiceFirstName() {
        return professionalServiceFirstName;
    }

    public void setProfessionalServiceFirstName(String professionalServiceFirstName) {
        this.professionalServiceFirstName = professionalServiceFirstName;
    }

    public String getProfessionalServiceLastName() {
        return professionalServiceLastName;
    }

    public void setProfessionalServiceLastName(String professionalServiceLastName) {
        this.professionalServiceLastName = professionalServiceLastName;
    }

    public String getProfessionalServicePhoneNumber() {
        return professionalServicePhoneNumber;
    }

    public void setProfessionalServicePhoneNumber(String professionalServicePhoneNumber) {
        this.professionalServicePhoneNumber = professionalServicePhoneNumber;
    }
}
