package services;

import models.service.CarServiceModel;

import java.util.List;

public interface CarService {

    List<CarServiceModel> getAll();

    void create(String brand, String model, String year, String engine);
}
