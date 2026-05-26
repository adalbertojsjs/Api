package org.example.cupon_api.infraestructure.adapter.http.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.cupon_api.application.*;
import org.example.cupon_api.domain.model.Cupon;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/cupones")
public class ControllerCupon {

    private final GetByIdUseCase idInPort;

    private final DeleteCuponUseCase deleteCuponInport;

    private final CreateCuponUseCase createCuponInPort;

    private final RedimirCuponUseCase redimirCuponUseCase;

    private final PublicarCuponUseCase publicarCuponUseCase;

    private  final ActualizarDescuentoUseCase actualizarDescuentoUseCase;


    @Operation(summary = "Cupon por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<Cupon> findById(@PathVariable UUID id){
        return ResponseEntity.ok(idInPort.findById(id));
    }


    @Operation(summary = "Crear Cupon",
            description = "Crear Cupon con los Datos ingresados")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Cupon creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno  del sistema ")})
    @PostMapping
    public ResponseEntity<Cupon> createdCupon(@RequestBody Cupon cupon){
        return ResponseEntity.status(HttpStatus.CREATED).body(createCuponInPort.crearCupon(cupon));
    }



    @Operation(summary = "Eliminar Cupon")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID id){

        deleteCuponInport.eliminarCupon(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Redimir cupón",
            description = "Permite redimir un cupón disponible usando su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupón redimido correctamente"),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
            @ApiResponse(responseCode = "400", description = "El cupón no puede ser redimido")
    })
    @GetMapping("/redimir/{id}")
    public ResponseEntity<Cupon> redimir(
            @Parameter(description = "ID del cupón")
            @PathVariable UUID id){

        return ResponseEntity.ok(redimirCuponUseCase.redimir(id));
    }


    @Operation(
            summary = "Actualizar descuento",
            description = "Actualiza el valor del descuento de un cupón."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
            @ApiResponse(responseCode = "400", description = "Descuento inválido")
    })
    @PatchMapping("/actualizar/{id}/descuento")
    public ResponseEntity<Cupon> actualizar(
            @Parameter(description = "ID del cupón")
            @PathVariable UUID id,

            @Parameter(description = "Nuevo valor del descuento")
            @RequestParam BigDecimal descuento){

        return ResponseEntity.ok(
                actualizarDescuentoUseCase.actualizarDescunto(id, descuento)
        );
    }


    @Operation(
            summary = "Publicar cupón",
            description = "Publica un cupón para dejarlo disponible a los clientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupón publicado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
            @ApiResponse(responseCode = "400", description = "El cupón no puede publicarse")
    })
    @PatchMapping("/{id}/publicar")
    public ResponseEntity<Cupon> publicar(
            @Parameter(description = "ID del cupón")
            @PathVariable UUID id){

        return ResponseEntity.ok(publicarCuponUseCase.publicar(id));
    }




}
