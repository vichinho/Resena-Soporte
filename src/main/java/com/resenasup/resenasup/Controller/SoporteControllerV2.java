package com.resenasup.resenasup.controller;

import com.resenasup.resenasup.assemblers.SoporteModelAssembler;
import com.resenasup.resenasup.model.Soporte;
import com.resenasup.resenasup.service.SoporteService;
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
@RequestMapping("/api/v2/soportes")
public class SoporteControllerV2 {

    private final SoporteService soporteService;
    private final SoporteModelAssembler assembler;

    public SoporteControllerV2(SoporteService soporteService, SoporteModelAssembler assembler) {
        this.soporteService = soporteService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<Soporte>> crearSoporte(@RequestBody @Valid Soporte soporte) {
        Soporte savedSoporte = soporteService.crearSoporte(soporte);
        return ResponseEntity
                .created(linkTo(methodOn(SoporteControllerV2.class).getSoporteById(savedSoporte.getIdSoporte())).toUri())
                .body(assembler.toModel(savedSoporte));
    }

    @GetMapping("/{id}")
    public EntityModel<Soporte> getSoporteById(@PathVariable Long id) {
        // Cambiar aquí para usar el método correcto
        Soporte soporte = soporteService.obtenerTodosSoportes().stream()
                .filter(s -> s.getIdSoporte().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Soporte no encontrado"));
        return assembler.toModel(soporte);
    }

    @PutMapping("/{id}/estado")
    public EntityModel<Soporte> actualizarEstado(@PathVariable Long id, @RequestBody String estado) {
        Soporte updatedSoporte = soporteService.actualizarEstado(id, estado);
        return assembler.toModel(updatedSoporte);
    }

    @PutMapping("/{id}/tipo")
    public EntityModel<Soporte> actualizarTipo(@PathVariable Long id, @RequestBody String tipo) {
        Soporte updatedSoporte = soporteService.actualizarTipo(id, tipo);
        return assembler.toModel(updatedSoporte);
    }

    @GetMapping
    public CollectionModel<EntityModel<Soporte>> getTodosSoportes() {
        List<EntityModel<Soporte>> soportes = soporteService.obtenerTodosSoportes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(soportes,
                linkTo(methodOn(SoporteControllerV2.class).getTodosSoportes()).withSelfRel());
    }
}
