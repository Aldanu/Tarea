package NominaSueldo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class CargarBaseDatos {
    @Bean
    CommandLineRunner initDatabase(RepositorioRider repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Rider("Alberto", "20", "6135091")));
            log.info("Preloading " + repository.save(new Rider("Alan", "21", "6435082")));
        };
    }
}
