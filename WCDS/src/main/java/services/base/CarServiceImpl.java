package services.base;

import models.entity.Car;
import models.entity.EngineType;
import models.service.CarServiceModel;
import org.modelmapper.ModelMapper;
import services.CarService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    @Inject
    public CarServiceImpl(ModelMapper modelMapper, EntityManager entityManager) {
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @Override
    public List<CarServiceModel> getAll() {
        return this.entityManager.createQuery("SELECT c FROM Car c", Car.class)
                .getResultList().stream()
                .map(car -> this.modelMapper.map(car, CarServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(String brand, String model, String year, String engine) {
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setEngineType(Enum.valueOf(EngineType.class, engine));

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(car);
        this.entityManager.getTransaction().commit();
    }
}
