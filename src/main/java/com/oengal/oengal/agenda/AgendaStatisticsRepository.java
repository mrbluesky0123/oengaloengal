package com.oengal.oengal.agenda;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AgendaStatisticsRepository extends JpaRepository<AgendaStatistics, Long> {

  Optional<AgendaStatistics> findByAgendaId(Long agendaID);

}
