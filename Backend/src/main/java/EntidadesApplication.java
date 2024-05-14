import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EntidadesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        // Launch Spring Boot application
        SpringApplication.run(EntidadesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Your code to be executed when the application starts
        System.out.println("Spring Boot application started...");
    }
}
