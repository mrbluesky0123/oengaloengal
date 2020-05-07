package com.oengal.oengal.agenda;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    Page<Agenda> findByDisplayYn(String displayYn, Pageable pageable);
    Optional<Agenda> findByAgendaIdAndDisplayYn(Long id, String displayYn);


}
