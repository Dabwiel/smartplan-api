/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gsm.smartplan.smartplanapi.controller;

import br.com.gsm.smartplan.smartplanapi.exception.ResourceNotFoundException;
import br.com.gsm.smartplan.smartplanapi.model.Professor;
import br.com.gsm.smartplan.smartplanapi.repository.ProfessorRepository;
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
@RequestMapping("/api")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    //Retorna dados de um determinado professor.
    @RequestMapping(method = RequestMethod.GET, path = "/professor/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(professorRepository.findById(id), HttpStatus.OK);
    }

    //Retorna todos os professores.
    @RequestMapping(method = RequestMethod.GET, path="/professor")
    public ResponseEntity<?> listOfProfessores() {
        return new ResponseEntity<>(professorRepository.findAll(), HttpStatus.OK);
    }

    //Cria um professor.
    @RequestMapping(method = RequestMethod.POST, path = "/professor/insert")
    public ResponseEntity<?> insertProfessor(@Valid @RequestBody Professor professor) {
        return new ResponseEntity<>(professorRepository.save(professor), HttpStatus.OK);
    }

    //Atualiza um determinado professor.
    @RequestMapping(method = RequestMethod.PUT, path = "/professor/{id}")
    public ResponseEntity<?> updateProfessor(@PathVariable("id") Long professor_id,
            @Valid @RequestBody Professor professor_details) {

        Professor professor = professorRepository.findById(professor_id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "professor", professor_id));

        //ATUALIZAR
        
        professor.setNome(professor_details.getNome());
        professor.setEmail(professor_details.getEmail());

        return new ResponseEntity<>(professorRepository.save(professor), HttpStatus.OK);
    }

    //Deleta um professor.
    @RequestMapping(method = RequestMethod.DELETE, path = "/professor/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable("id") Long professor_id) {
        Professor professor = professorRepository.findById(professor_id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "id", professor_id));

        professorRepository.delete(professor);

        return ResponseEntity.ok().build();
    }
}
