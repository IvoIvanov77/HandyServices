package org.softuni.handy.domain.models.view;

public class LocationViewModel {

    private String id;

    private String town;

    private String locationPicture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getLocationPicture() {
        return locationPicture;
    }

    public void setLocationPicture(String locationPicture) {
        this.locationPicture = locationPicture;
    }
}
