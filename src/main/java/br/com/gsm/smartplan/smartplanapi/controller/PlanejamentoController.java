/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gsm.smartplan.smartplanapi.controller;

import br.com.gsm.smartplan.smartplanapi.exception.ResourceNotFoundException;
import br.com.gsm.smartplan.smartplanapi.model.Planejamento;
import br.com.gsm.smartplan.smartplanapi.repository.PlanejamentoRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gabriel San Martin
 */

@RestController
@RequestMapping("/planejamentos")
public class PlanejamentoController {
    
    @Autowired
    private PlanejamentoRepository planejamentoRepository;

    //Retorna dados de um determinado planejamento.
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(planejamentoRepository.findById(id), HttpStatus.OK);
    }

    //Retorna todos os planejamentos.
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listOfPlanejamentos() {
        return new ResponseEntity<>(planejamentoRepository.findAll(), HttpStatus.OK);
    }

    //Cria um planejamento.
    @RequestMapping(method = RequestMethod.POST, path = "/cadastrar")
    public ResponseEntity<?> insertPlanejamento(@Valid @RequestBody Planejamento planejamento) {
        return new ResponseEntity<>(planejamentoRepository.save(planejamento), HttpStatus.OK);
    }

    //Atualiza um determinado professor.
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<?> updatePlanejamento(@PathVariable("id") Long planejamento_id,
            @Valid @RequestBody Planejamento planejamento_details) {

        Planejamento planejamento = planejamentoRepository.findById(planejamento_id)
                .orElseThrow(() -> new ResourceNotFoundException("Planejamento", "planejamento", planejamento_id));

        planejamento.setCor(planejamento_details.getCor());
        planejamento.setNome(planejamento_details.getNome());
        planejamento.setDescricao(planejamento_details.getDescricao());
        planejamento.setDataInicio(planejamento_details.getDataInicio());
        planejamento.setDataFinal(planejamento_details.getDataFinal());

        return new ResponseEntity<>(planejamentoRepository.save(planejamento), HttpStatus.OK);
    }
    
    //Deleta um planejamento.
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<?> deletePlanejamento(@PathVariable("id") Long planejamento_id) {
        Planejamento planejamento = planejamentoRepository.findById(planejamento_id)
                .orElseThrow(() -> new ResourceNotFoundException("Planejamento", "id", planejamento_id));

        planejamentoRepository.delete(planejamento);

        return ResponseEntity.ok().build();
    }
}
