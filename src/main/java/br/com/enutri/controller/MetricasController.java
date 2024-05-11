package br.com.enutri.controller;

import br.com.enutri.service.MetricasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/metricas")
@Tag(name="Metricas")
public class MetricasController {

    @Autowired
    MetricasService metricasService;

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Map<String, Map<String, Integer>>> metricas(@PathVariable Long idPaciente){
        Map<String, Map<String, Integer>> metricas = metricasService.adesaoTag(
                idPaciente,
                LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 30)
        );

        return ResponseEntity.status(HttpStatus.OK).body(metricas);
    }
}
