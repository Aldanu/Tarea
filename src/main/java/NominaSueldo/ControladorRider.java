package NominaSueldo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class ControladorRider {

    private final RepositorioRider repository;

    ControladorRider(RepositorioRider repository) {
        this.repository = repository;
    }



    @GetMapping("/riders")
    Resources<Resource<Rider>> all() {

        List<Resource<Rider>> riders = repository.findAll().stream()
                .map(rider -> new Resource<>(rider,
                        linkTo(methodOn(ControladorRider.class).one(rider.getId())).withSelfRel(),
                        linkTo(methodOn(ControladorRider.class).all()).withRel("employees")))
                .collect(Collectors.toList());

        return new Resources<>(riders,
                linkTo(methodOn(ControladorRider.class).all()).withSelfRel());
    }

    @PostMapping("/riders")
    Rider nuevoRider(@RequestBody Rider nuevoRider) {
        return repository.save(nuevoRider);
    }

    @GetMapping("/employees/{id}")
    Resource<Rider> one(@PathVariable Long id) {

        Rider employee = repository.findById(id)
                .orElseThrow(() -> new RiderNotFoundException(id));

        return new Resource<>(employee,
                linkTo(methodOn(ControladorRider.class).one(id)).withSelfRel(),
                linkTo(methodOn(ControladorRider.class).all()).withRel("employees"));
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
