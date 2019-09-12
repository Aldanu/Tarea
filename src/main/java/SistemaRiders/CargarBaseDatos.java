package SistemaRiders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class CargarBaseDatos {
    @Bean
    CommandLineRunner initDatabase(RepositorioRider repository, RepositorioSolicitud repositorioSolicitud) {
        return args -> {
            log.info("Precargando " + repository.save(new Rider("Alberto", "Perez", "20", "6135091")));
            log.info("Precargando " + repository.save(new Rider("Alan", "Walker", "21", "6435082")));

            repositorioSolicitud.save(new Solicitud("Av. Busch y Calle PuertoRico", Status.COMPLETED));
            repositorioSolicitud.save(new Solicitud("Av. Principal Calacoto y Calle 16", Status.IN_PROGRESS));

            repositorioSolicitud.findAll().forEach(order -> {
                log.info("Precargando " + order);
            });
        };
    }
}
