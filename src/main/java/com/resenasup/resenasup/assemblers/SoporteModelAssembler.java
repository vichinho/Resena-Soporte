package com.resenasup.resenasup.assemblers;
import com.resenasup.resenasup.controller.SoporteControllerV2;
import com.resenasup.resenasup.model.Soporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Soporte, EntityModel<Soporte>> {
    @Override
    public EntityModel<Soporte> toModel(Soporte soporte) {
        return EntityModel.of(soporte,
            linkTo(methodOn(SoporteControllerV2.class).getSoporteById(soporte.getIdSoporte())).withSelfRel(),
            linkTo(methodOn(SoporteControllerV2.class).getTodosSoportes()).withRel("soportes"),
            linkTo(methodOn(SoporteControllerV2.class).actualizarEstado(soporte.getIdSoporte(), null)).withRel("update-estado"),
            linkTo(methodOn(SoporteControllerV2.class).actualizarTipo(soporte.getIdSoporte(), null)).withRel("update-tipo")
        );
    }
}