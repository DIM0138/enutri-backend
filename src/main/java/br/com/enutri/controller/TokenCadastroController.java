package br.com.enutri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.TokenCadastro;
import br.com.enutri.model.dto.TokenCadastroDTO;
import br.com.enutri.service.TokenCadastroService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/token")
public class TokenCadastroController {

    @Autowired
    private TokenCadastroService tokenService;

    @GetMapping("/novo")
    public ResponseEntity<TokenCadastroDTO> getToken(@RequestParam String nomePaciente, @RequestParam String idNutricionista) {

        TokenCadastro novoToken = tokenService.generateNewToken(nomePaciente, Long.parseLong(idNutricionista));
        TokenCadastroDTO tokenDTO = new TokenCadastroDTO(novoToken);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    }

    @GetMapping("/{token}")
    public ResponseEntity<TokenCadastroDTO> getToken(@PathVariable String token) {
        
        TokenCadastro tokenConsultado = tokenService.getByToken(token);
        TokenCadastroDTO tokenDTO = new TokenCadastroDTO(tokenConsultado);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    }
    
}
