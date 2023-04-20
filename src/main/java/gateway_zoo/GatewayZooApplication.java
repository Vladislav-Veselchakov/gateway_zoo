package gateway_zoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayZooApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayZooApplication.class, args);
        System.err.println("App started");
    }

}
