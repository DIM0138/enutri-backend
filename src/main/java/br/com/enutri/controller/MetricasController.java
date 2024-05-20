package br.com.enutri.controller;

import br.com.enutri.model.metricas.Metrica;
import br.com.enutri.service.MetricasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/metricas")
@Tag(name="Metricas")
public class MetricasController {

    @Autowired
    MetricasService metricasService;

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Metrica> metricas(
            @PathVariable Long idPaciente,
            @RequestParam(value = "inicio", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate inicio,
            @RequestParam(value = "fim", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fim
    ){
        Metrica metricas = metricasService.getPacienteMetricas(idPaciente, inicio, fim);
        return ResponseEntity.status(HttpStatus.OK).body(metricas);
    }
}
