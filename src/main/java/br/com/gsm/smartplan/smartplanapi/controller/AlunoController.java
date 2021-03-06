/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gsm.smartplan.smartplanapi.controller;

import br.com.gsm.smartplan.smartplanapi.exception.ResourceNotFoundException;
import br.com.gsm.smartplan.smartplanapi.model.Aluno;
import br.com.gsm.smartplan.smartplanapi.model.Turma;
import br.com.gsm.smartplan.smartplanapi.repository.AlunoRepository;
import br.com.gsm.smartplan.smartplanapi.repository.TurmaRepository;
import java.util.List;
import javax.transaction.Transactional;
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
public class AlunoController {

    @Autowired
    private TurmaRepository turmaRepository;
    
    @Autowired
    private AlunoRepository alunoRepository;

    //Retorna dados de um determinado aluno.
    @RequestMapping(method = RequestMethod.GET, path = "/aluno/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(alunoRepository.findById(id), HttpStatus.OK);
    }

    //Retorna todos os planejamentos.
    @RequestMapping(method = RequestMethod.GET, path = "/aluno")
    public ResponseEntity<?> listOfAlunos() {
        return new ResponseEntity<>(alunoRepository.findAll(), HttpStatus.OK);
    }

    //Cria um planejamento.
    @RequestMapping(method = RequestMethod.POST, path = "/aluno/insert")
    public ResponseEntity<?> insertAluno(@Valid @RequestBody Aluno aluno) {
        return new ResponseEntity<>(alunoRepository.save(aluno), HttpStatus.OK);
    }

    //Atualiza um determinado professor.
    @RequestMapping(method = RequestMethod.PUT, path = "/aluno/{id}")
    public ResponseEntity<?> updateAluno(@PathVariable("id") Long aluno_id,
            @Valid @RequestBody Aluno aluno_details) {

        Aluno aluno = alunoRepository.findById(aluno_id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "aluno", aluno_id));

        aluno.setNome(aluno_details.getNome());
        aluno.setEmail(aluno_details.getEmail());

        //TALVEZ UM SET PARA AS NOTAS AQUI
        //VOU VER DEPOIS, TÁ?
        //NÃO ESQUECE
        return new ResponseEntity<>(alunoRepository.save(aluno), HttpStatus.OK);
    }

    //Deleta um aluno.
    @RequestMapping(method = RequestMethod.DELETE, path = "/aluno/{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable("id") Long aluno_id) {
        Aluno aluno = alunoRepository.findById(aluno_id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "id", aluno_id));

        alunoRepository.delete(aluno);

        return ResponseEntity.ok().build();
    }
    
    //Retorna os alunos de uma determinada turma
    @RequestMapping(method = RequestMethod.GET, path = "/turma/{id}/alunos")
    public ResponseEntity<?> getAlunosByTurmaId(@PathVariable("id") Long id){
        return new ResponseEntity<>(alunoRepository.findByTurmaId(id), HttpStatus.OK);
    }
    
    //Retorna o número de alunos que o professor possui.
    @RequestMapping(method = RequestMethod.GET, path = "/professor/{id}/turmas/alunos/count")
    @Transactional
    public ResponseEntity<?> getNumberOfTurmasByProfessorId(@PathVariable("id") Long id) {
        List<Turma> turmas = turmaRepository.findByProfessorId(id);
        int count = 0;
        for(Turma t : turmas) count += t.getAlunos().size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
