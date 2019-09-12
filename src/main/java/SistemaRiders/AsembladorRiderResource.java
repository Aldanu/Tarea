package SistemaRiders;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class AsembladorRiderResource implements ResourceAssembler<Rider, Resource<Rider>> {

    @Override
    public Resource<Rider> toResource(Rider rider) {

        return new Resource<>(rider,
                linkTo(methodOn(ControladorRider.class).one(rider.getId())).withSelfRel(),
                linkTo(methodOn(ControladorRider.class).all()).withRel("riders"));
    }
}