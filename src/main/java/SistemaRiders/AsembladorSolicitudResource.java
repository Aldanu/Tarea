package SistemaRiders;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class AsembladorSolicitudResource implements ResourceAssembler<Solicitud, Resource<Solicitud>> {

    @Override
    public Resource<Solicitud> toResource(Solicitud solicitud) {

        // Unconditional links to single-item resource and aggregate root

        Resource<Solicitud> solicitudResource = new Resource<>(solicitud,
                linkTo(methodOn(ControladorSolicitud.class).one(solicitud.getId())).withSelfRel(),
                linkTo(methodOn(ControladorSolicitud.class).all()).withRel("orders")
        );

        // Conditional links based on state of the order

        if (solicitud.getStatus() == Status.IN_PROGRESS) {
            solicitudResource.add(
                    linkTo(methodOn(ControladorSolicitud.class)
                            .cancel(solicitud.getId())).withRel("cancel"));
            solicitudResource.add(
                    linkTo(methodOn(ControladorSolicitud.class)
                            .complete(solicitud.getId())).withRel("complete"));
        }

        return solicitudResource;
    }
}