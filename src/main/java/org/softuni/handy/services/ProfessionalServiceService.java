package org.softuni.handy.services;

import org.softuni.handy.domain.models.service.ProfessionalServiceModel;

import java.util.List;

public interface ProfessionalServiceService {
    boolean registerService(ProfessionalServiceModel serviceModel);

    List<ProfessionalServiceModel> getAllByStatus(String status);

    List<ProfessionalServiceModel> getAllByUsername(String username);

    ProfessionalServiceModel getOneByID(String id);

    boolean editService(ProfessionalServiceModel serviceModel);

    List<ProfessionalServiceModel> searchByLocationAndType(List<String> locations,
                                                           List<String> types);
}
