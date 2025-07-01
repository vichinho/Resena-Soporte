package com.resenasup.resenasup.assemblers;

import com.resenasup.resenasup.controller.ResenaControllerV2;
import com.resenasup.resenasup.model.Resena;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<Resena, EntityModel<Resena>> {

    @Override
    public EntityModel<Resena> toModel(Resena resena) {
        return EntityModel.of(resena,
            linkTo(methodOn(ResenaControllerV2.class).getResenaById(resena.getIdResena())).withSelfRel(),
            linkTo(methodOn(ResenaControllerV2.class).getTodasResenas()).withRel("resenas"),
            linkTo(methodOn(ResenaControllerV2.class).eliminarResena(resena.getIdResena())).withRel("delete"),
            linkTo(methodOn(ResenaControllerV2.class).getResenasByProducto(resena.getIdProducto())).withRel("resenas-by-producto")
        );
    }
}
