package NominaSueldo;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorRider {

    private final RepositorioRider repository;

    ControladorRider(RepositorioRider repository) {
        this.repository = repository;
    }

    @GetMapping("/riders")
    List<Rider> all() {
        return repository.findAll();
    }

    @PostMapping("/riders")
    Rider nuevoRider(@RequestBody Rider nuevoRider) {
        return repository.save(nuevoRider);
    }

    @GetMapping("/riders/{id}")
    Rider one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RiderNotFoundException(id));
    }

    @PutMapping("/riders/{id}")
    Rider reemplazarRider(@RequestBody Rider nuevoRider, @PathVariable Long id) {

        return repository.findById(id)
                .map(rider -> {
                    rider.setNombre(nuevoRider.getNombre());
                    rider.setEdad(nuevoRider.getEdad());
                    rider.setCI(nuevoRider.getCI());
                    return repository.save(rider);
                })
                .orElseGet(() -> {
                    nuevoRider.setId(id);
                    return repository.save(nuevoRider);
                });
    }

    @DeleteMapping("/riders/{id}")
    void borrarRider(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
