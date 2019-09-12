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

    private final AsembladorRiderResource assembler;

    ControladorRider(RepositorioRider repository,
                       AsembladorRiderResource assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }



    @GetMapping("/riders")
    Resources<Resource<Rider>> all() {

        List<Resource<Rider>> riders = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(riders,
                linkTo(methodOn(ControladorRider.class).all()).withSelfRel());
    }

    @PostMapping("/riders")
    Rider nuevoRider(@RequestBody Rider nuevoRider) {
        return repository.save(nuevoRider);
    }

    @GetMapping("/riders/{id}")
    Resource<Rider> one(@PathVariable Long id) {

        Rider rider = repository.findById(id)
                .orElseThrow(() -> new RiderNotFoundException(id));

        return assembler.toResource(rider);
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
