/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gsm.smartplan.smartplanapi.repository;

import br.com.gsm.smartplan.smartplanapi.model.Turma;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel San Martin
 */

@Component
public interface TurmaRepository extends JpaRepository<Turma, Long>{
    public List<Turma> findByProfessorId(Long professorId);
    public List<Turma> findByPlanejamentoId(Long planejamentoId);
    
    @Query(value = "select * from turmas as t where t.nome like '%?%'", nativeQuery = true)
    public List<Turma> searchByName(String search);
}
