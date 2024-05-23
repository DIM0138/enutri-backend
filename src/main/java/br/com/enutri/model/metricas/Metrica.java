package br.com.enutri.model.metricas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Metrica {
    AdesaoData adesaoTag;
    AdesaoData adesaoEmocao;
    Map<String, Integer> quantidadeQualidadeSono = new HashMap<>();
    Map<String, Integer> quantidadeEmocao = new HashMap<>();
    List<String> sintomas = new ArrayList<>();
}
