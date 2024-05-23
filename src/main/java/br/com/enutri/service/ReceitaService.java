package br.com.enutri.service;

import br.com.enutri.exception.DeleteOperationException;
import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.model.Ingrediente;
import br.com.enutri.model.IngredienteReceita;
import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.ReceitaDTO;
import br.com.enutri.repository.IngredienteRepository;
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private NutricionistaRepository nutricionistaRepository;
    @Autowired
    private IngredienteRepository ingredienteRepository;

    public Receita save(ReceitaDTO receitaDTO) {
        for (IngredienteReceita ingredienteReceita : receitaDTO.getIngredientes()){
            Ingrediente ingredienteExistente = ingredienteRepository.findOneByNomeIgnoreCaseAndMedida(
                    ingredienteReceita.getIngrediente().getNome(), ingredienteReceita.getIngrediente().getMedida());
            if (ingredienteExistente != null) {
                ingredienteReceita.setIngrediente(ingredienteExistente);
            } else {
                Ingrediente ingredienteNovo = ingredienteRepository.save(ingredienteReceita.getIngrediente());
                ingredienteReceita.setIngrediente(ingredienteNovo);
            }
        }

        long idNutricionista = receitaDTO.getNutricionista();
        Optional<Nutricionista> nutricionistaExistente = nutricionistaRepository.findById(idNutricionista);
        return nutricionistaExistente.map(nutricionista -> {
            Receita novaReceita = Receita.builder()
                    .id(receitaDTO.getId())
                    .nutricionista(nutricionista)
                    .tipoRefeicao(receitaDTO.getTipoRefeicao())
                    .nome(receitaDTO.getNome())
                    .descricao(receitaDTO.getDescricao())
                    .tempoPreparo(receitaDTO.getTempoPreparo())
                    .calorias(receitaDTO.getCalorias())
                    .imagemURL(receitaDTO.getImagemURL())
                    .modoPreparo(receitaDTO.getModoPreparo())
                    .ingredientes(receitaDTO.getIngredientes())
                    .contemAlergicos(receitaDTO.getContemAlergicos())
                    .alergicos(receitaDTO.getAlergicos())
                    .build();

            return receitaRepository.save(novaReceita);
        }).orElseThrow(() -> new ResourceNotFoundException("Nutricionista com id "+idNutricionista+" não encontrado."));
    }

    public Receita atualizar(Long id, ReceitaDTO receitaDTO) {
         return receitaRepository.findById(id).map(receitaExistente -> {
             Optional.ofNullable(receitaDTO.getTipoRefeicao()).ifPresent(receitaExistente::setTipoRefeicao);
             Optional.ofNullable(receitaDTO.getNome()).ifPresent(receitaExistente::setNome);
             Optional.ofNullable(receitaDTO.getDescricao()).ifPresent(receitaExistente::setDescricao);
             Optional.ofNullable(receitaDTO.getTempoPreparo()).ifPresent(receitaExistente::setTempoPreparo);
             Optional.ofNullable(receitaDTO.getCalorias()).ifPresent(receitaExistente::setCalorias);
             Optional.ofNullable(receitaDTO.getImagemURL()).ifPresent(receitaExistente::setImagemURL);
             Optional.ofNullable(receitaDTO.getModoPreparo()).ifPresent(receitaExistente::setModoPreparo);
             Optional.ofNullable(receitaDTO.getIngredientes()).ifPresent(receitaExistente::setIngredientes);
             Optional.ofNullable(receitaDTO.getContemAlergicos()).ifPresent(receitaExistente::setContemAlergicos);
             Optional.ofNullable(receitaDTO.getAlergicos()).ifPresent(receitaExistente::setAlergicos);
             return receitaRepository.save(receitaExistente);
         }).orElseThrow(() -> new ResourceNotFoundException("Receita "+id+" não encontrada."));
    }

    public List<ReceitaDTO> retriveAllReceitas() {
        return receitaRepository.findAll().stream().map(ReceitaDTO::new).toList();
    }

    public void delete(Long id) {
        if (!receitaRepository.existsById(id)){
            throw new ResourceNotFoundException("Receita não encontrada para remoção");
        }
        try {
            receitaRepository.deleteById(id);
        } catch (Exception e){
            throw new DeleteOperationException("Não foi possível deletar a receita com id: " + id);
        }
    }

    public Receita getById (long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita "+id+" não encontrada."));
    }
}