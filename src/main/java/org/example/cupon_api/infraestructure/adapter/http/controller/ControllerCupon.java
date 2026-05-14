package org.example.cupon_api.infraestructure.adapter.http.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.cupon_api.application.CreateCuponUseCase;
import org.example.cupon_api.application.DeleteCuponUseCase;
import org.example.cupon_api.application.GetByIdUseCase;
import org.example.cupon_api.domain.model.Cupon;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/cupones")
public class ControllerCupon {

    private final GetByIdUseCase idInPort;

    private final DeleteCuponUseCase deleteCuponInport;

    private final CreateCuponUseCase createCuponInPort;


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




}
