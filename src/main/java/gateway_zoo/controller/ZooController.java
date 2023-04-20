package gateway_zoo.controller;

import gateway_zoo.RandomAnimalClient;
import gateway_zoo.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZooController {
    @Autowired
    private RandomAnimalClient randomAnimalClient;

    @GetMapping("/animals/any")
    ResponseEntity<Animal> seeAnyAnimal(){
        return randomAnimalClient.random1();
    }
}