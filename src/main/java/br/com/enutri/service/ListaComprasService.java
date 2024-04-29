package br.com.enutri.service;

import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.model.*;
import br.com.enutri.repository.ListaComprasRepository;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.PlanoAlimentarRepository;
import br.com.enutri.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListaComprasService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ListaComprasRepository listaComprasRepository;

    @Autowired
    private PlanoAlimentarRepository planoAlimentarRepository;


    private List<Receita> getReceitas (PlanoAlimentar planoAlimentar){
        List<Receita> receitas = new ArrayList<>();
        for (RegistroDiario registroDiario : planoAlimentar.getRegistrosDiarios()){
            for (Refeicao refeicao : registroDiario.getRefeicoes()){
                receitas.add(refeicao.getReceitaEscolhida());
            }
        }
        return receitas;
    };

    public ListaCompras gerarListaItens(Long idPlanoAlimentar) {
        PlanoAlimentar planoAlimentar = planoAlimentarRepository.findById(idPlanoAlimentar)
                .orElseThrow(() -> new ResourceNotFoundException(("Plano alimentar com id "+idPlanoAlimentar+" não encontrado.")));

        List<Receita> receitas = getReceitas(planoAlimentar);

        Map<Ingrediente, Integer> quantidadeIngredientes = new HashMap<>();

        for (Receita receita : receitas){
            for (IngredienteReceita ingredienteReceita : receita.getIngredientes()) {
                Ingrediente ingrediente = ingredienteReceita.getIngrediente();
                Integer quantidade = ingredienteReceita.getQuantidade();

                quantidadeIngredientes.put(ingrediente, quantidadeIngredientes.getOrDefault(ingrediente,0)+quantidade);
            }
        }

        List<ItemListaCompras> itens = new ArrayList<>();
        for (Map.Entry<Ingrediente, Integer> entry : quantidadeIngredientes.entrySet()) {
            Ingrediente ingrediente = entry.getKey();
            Integer quantidade = entry.getValue();

            ItemListaCompras item = new ItemListaCompras();

            item.setIngrediente(ingrediente.getNome());
            item.setMetrica(String.valueOf(ingrediente.getMedida()));
            item.setQuantidadeTotal(quantidade);

            itens.add(item);
        }
        ListaCompras listaCompras = new ListaCompras();
        listaCompras.setItens(itens);

        return listaComprasRepository.save(listaCompras);
    }
}
