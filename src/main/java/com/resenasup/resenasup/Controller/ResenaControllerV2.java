package com.resenasup.resenasup.controller;

import com.resenasup.resenasup.assemblers.ResenaModelAssembler;
import com.resenasup.resenasup.model.Resena;
import com.resenasup.resenasup.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/resenas")
public class ResenaControllerV2 {

    private final ResenaService resenaService;
    private final ResenaModelAssembler assembler;

    public ResenaControllerV2(ResenaService resenaService, ResenaModelAssembler assembler) {
        this.resenaService = resenaService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<Resena>> crearResena(@RequestBody @Valid Resena resena) {
        Resena savedResena = resenaService.crearResena(resena);
        return ResponseEntity
            .created(linkTo(methodOn(ResenaControllerV2.class).getResenaById(savedResena.getIdResena())).toUri())
            .body(assembler.toModel(savedResena));
    }

    @GetMapping("/{id}")
    public EntityModel<Resena> getResenaById(@PathVariable Long id) {
        Resena resena = resenaService.obtenerTodasResenas().stream()
            .filter(r -> r.getIdResena().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));
        return assembler.toModel(resena);
    }

    @GetMapping
    public CollectionModel<EntityModel<Resena>> getTodasResenas() {
        List<EntityModel<Resena>> resenas = resenaService.obtenerTodasResenas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(resenas,
            linkTo(methodOn(ResenaControllerV2.class).getTodasResenas()).withSelfRel());
    }

    @GetMapping("/producto/{idProducto}")
    public CollectionModel<EntityModel<Resena>> getResenasByProducto(@PathVariable String idProducto) {
        List<EntityModel<Resena>> resenas = resenaService.obtenerResenasPorProducto(idProducto).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(resenas,
            linkTo(methodOn(ResenaControllerV2.class).getResenasByProducto(idProducto)).withSelfRel(),
            linkTo(methodOn(ResenaControllerV2.class).getTodasResenas()).withRel("all-resenas"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        resenaService.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }
}
