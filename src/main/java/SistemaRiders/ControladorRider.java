package SistemaRiders;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> newRider(@RequestBody Rider newRider) throws URISyntaxException {

        Resource<Rider> resource = assembler.toResource(repository.save(newRider));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/riders/{id}")
    Resource<Rider> one(@PathVariable Long id) {

        Rider rider = repository.findById(id)
                .orElseThrow(() -> new RiderNotFoundException(id));

        return assembler.toResource(rider);
    }

    @PutMapping("/riders/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Rider newRider, @PathVariable Long id) throws URISyntaxException {

        Rider updatedRider = repository.findById(id)
                .map(rider -> {
                    rider.setNombre(rider.getNombre());
                    rider.setEdad(rider.getEdad());
                    rider.setCI(rider.getCI());
                    return repository.save(rider);
                })
                .orElseGet(() -> {
                    newRider.setId(id);
                    return repository.save(newRider);
                });

        Resource<Rider> resource = assembler.toResource(updatedRider);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/riders/{id}")
    ResponseEntity<?> deleteRider(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
