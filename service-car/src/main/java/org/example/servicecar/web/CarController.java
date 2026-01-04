package org.example.servicecar.web;
import org.example.servicecar.entities.Car;
import org.example.servicecar.repositories.CarRepository;
import org.example.servicecar.services.ClientApi;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarRepository repo;
    private final ClientApi clientApi;

    public CarController(CarRepository repo, ClientApi clientApi) {
        this.repo = repo;
        this.clientApi = clientApi;
    }

    @PostMapping
    public Car save(@RequestBody Car car) {
        return repo.save(car);
    }

    @GetMapping
    public List<Car> findAll() {
        List<Car> cars = repo.findAll();
        for (Car car : cars) {
            if (car.getClientId() != null) {
                car.setClient(clientApi.findClientById(car.getClientId()));
            }
        }
        return cars;
    }

    @GetMapping("/byClient/{clientId}")
    public List<Car> findByClient(@PathVariable Long clientId) {
        List<Car> cars = repo.findByClientId(clientId);
        for (Car car : cars) {
            car.setClient(clientApi.findClientById(clientId));
        }
        return cars;
    }
}
