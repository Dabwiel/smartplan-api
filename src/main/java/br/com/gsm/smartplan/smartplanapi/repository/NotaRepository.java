/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gsm.smartplan.smartplanapi.repository;

import br.com.gsm.smartplan.smartplanapi.model.Nota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aluno
 */

@Component
public interface NotaRepository extends JpaRepository<Nota, Long>{
    public List<Nota> findByAlunoId(Long alunoId);
}
