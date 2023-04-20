package gateway_zoo;

import gateway_zoo.model.Animal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RandomAnimalClient {

    private final RestTemplate simpleRestTemplate;
    private final RestTemplate loadBalancedTemplate;
    private final DiscoveryClient discoveryClient;

    RandomAnimalClient(@Qualifier("simpleTemplate") RestTemplate simpleRestTemplate,
                       @LoadBalanced RestTemplate loadBalancedTemplate,
                       DiscoveryClient discoveryClient) {
        this.simpleRestTemplate = simpleRestTemplate;
        this.loadBalancedTemplate = loadBalancedTemplate;
        this.discoveryClient = discoveryClient;
    }


    public ResponseEntity<Animal> random1() {
        System.out.println("Sending  request for animal {}");
        ResponseEntity<Animal> response = loadBalancedTemplate.getForEntity("http://proxy/random-animal/random", Animal.class);
        System.out.println("name: " + response.getBody());
        return response;
    }

    public ResponseEntity<Animal> random() {
        System.out.println(discoveryClient.getInstances("proxy"));
        ServiceInstance instance = discoveryClient.getInstances("proxy")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("proxy service unavailable"));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(instance.getUri().toString() + "/random-animal/random");
        return simpleRestTemplate.getForEntity(uriComponentsBuilder.toUriString(), Animal.class);
    }
}