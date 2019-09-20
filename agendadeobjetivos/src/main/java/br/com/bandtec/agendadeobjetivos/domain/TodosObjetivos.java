package br.com.bandtec.agendadeobjetivos.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosObjetivos extends JpaRepository<Objetivo, Long> {
	@Query("select o from Objetivo o where o.dataMaximaParaExecucao <= :data")
	public List<Objetivo> ate(LocalDate data);

}