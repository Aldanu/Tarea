package SistemaRiders;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class ControladorSolicitud {

    private final RepositorioSolicitud repositorioSolicitud;
    private final AsembladorSolicitudResource assembler;

    ControladorSolicitud(RepositorioSolicitud repositorioSolicitud,
                         AsembladorSolicitudResource assembler) {

        this.repositorioSolicitud = repositorioSolicitud;
        this.assembler = assembler;
    }

    @GetMapping("/solicitud")
    Resources<Resource<Solicitud>> all() {

        List<Resource<Solicitud>> solicitudes = repositorioSolicitud.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(solicitudes,
                linkTo(methodOn(ControladorSolicitud.class).all()).withSelfRel());
    }

    @GetMapping("/solicitud/{id}")
    Resource<Solicitud> one(@PathVariable Long id) {
        return assembler.toResource(
                repositorioSolicitud.findById(id)
                        .orElseThrow(() -> new RepositorioNotFoundException(id)));
    }

    @PostMapping("/solicitud")
    ResponseEntity<Resource<Solicitud>> newSolicitud(@RequestBody Solicitud solicitud) {

        solicitud.setStatus(Status.IN_PROGRESS);
        Solicitud newSolicitud = repositorioSolicitud.save(solicitud);

        return ResponseEntity
                .created(linkTo(methodOn(ControladorSolicitud.class).one(newSolicitud.getId())).toUri())
                .body(assembler.toResource(newSolicitud));
    }
}