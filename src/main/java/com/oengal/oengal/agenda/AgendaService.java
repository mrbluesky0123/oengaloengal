package com.oengal.oengal.agenda;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@Data
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class AgendaService {

  private AgendaRepository agendaRepository;
  private AgendaStatisticsRepository agendaStatisticsRepository;

  @Autowired
  public AgendaService(AgendaRepository agendaRepository,
      AgendaStatisticsRepository agendaStatisticsRepository) {

    this.agendaRepository = agendaRepository;
    this.agendaStatisticsRepository = agendaStatisticsRepository;

  }


  // Select agenda list
  public List<AgendaResponse> getAgendaList(int pageNum, int pageSize, String sort) {

    List<AgendaResponse> agendaResponseList = new ArrayList<>();

    // Get agendas
    PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(sort).descending());
    Page<Agenda> agendaPage = agendaRepository.findByDisplayYn("Y", pageRequest);
    if(!agendaPage.hasContent()) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No agendas.");
    }
    List<Agenda> agendaList = agendaPage.getContent();

    // Make reponse with agenda, agenda statistics
    for(Agenda agenda: agendaList){
      AgendaStatistics agendaStatistics = null;

      try {
        agendaStatistics = this.agendaStatisticsRepository.findByAgendaId(
            agenda.getAgendaId())
            .orElseThrow(() -> new NoSuchElementException());
      } catch(NoSuchElementException e) {
        agendaStatistics = AgendaStatistics.builder().agendaId(agenda.getAgendaId())
            .hitCount(0)
            .likeIt(0)
            .dislikeIt(0)
            .build();
        // throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
      }
      AgendaResponse agendaResponse = new AgendaResponse(agenda, agendaStatistics);
      agendaResponseList.add(agendaResponse);

    }


    return agendaResponseList;

  }


  // Select single agenda
  public AgendaResponse getAgenda(Long id) {

    Agenda agenda = null;
    AgendaStatistics agendaStatistics = null;

    // Get single agenda
    agenda = this.agendaRepository.findByAgendaIdAndDisplayYn(id, "Y")
        .orElseThrow(() -> new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists."));
    // Get single agenda statistics
    agendaStatistics = this.agendaStatisticsRepository.findByAgendaId(id)
        .orElseThrow(() -> new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda statistics exists."));

    int hitCount = agendaStatistics.getHitCount();
    log.error("#### " + agendaStatistics.getHitCount());
    agendaStatistics.setHitCount(++hitCount);
    // need exception handling
    log.error("#### " + agendaStatistics.getHitCount());
    this.agendaStatisticsRepository.save(agendaStatistics);

    return new AgendaResponse(agenda, agendaStatistics);

  }

  // Post agenda

  public AgendaResponse postAgenda(Agenda agenda) {

    agenda.setDisplayYn("Y");
    System.out.println("#####" + TransactionSynchronizationManager.getCurrentTransactionName());
    // Post agenda
    // Need transaction operation
    Agenda newAgenda = this.agendaRepository.save(agenda);

    AgendaStatistics newAgendaStatistics = AgendaStatistics.builder()
        .agendaId(agenda.getAgendaId())
        .likeIt(0)
        .dislikeIt(0)
        .build();

    newAgendaStatistics = this.agendaStatisticsRepository.save(newAgendaStatistics);

    return new AgendaResponse(newAgenda, newAgendaStatistics);

  }

  // Modify agenda
  public Agenda modifyAgenda(Agenda modifiedAgenda) {

    if(modifiedAgenda.getAgendaId() == null) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -101, "Id cannot be null.");
    }

    // Make agenda immutable
    Agenda targetAgenda = this.agendaRepository.findByAgendaIdAndDisplayYn(
          modifiedAgenda.getAgendaId(), "Y")
          .orElseThrow(() -> new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists."));
      targetAgenda.setCategory(modifiedAgenda.getCategory());
      targetAgenda.setSubject(modifiedAgenda.getSubject());
      targetAgenda.setContents(modifiedAgenda.getContents());
      targetAgenda.setTag1(modifiedAgenda.getTag1());
      targetAgenda.setTag2(modifiedAgenda.getTag2());
      targetAgenda.setTag3(modifiedAgenda.getTag3());
      targetAgenda.setUpdDt(LocalDateTime.now());

    return this.agendaRepository.save(targetAgenda);

  }

  // Delete agenda
  public Agenda deleteAgenda(Long id) {

    Agenda targetAgenda = this.agendaRepository.findByAgendaIdAndDisplayYn(id, "Y")
        .orElseThrow(() -> new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists."));
    targetAgenda.setDisplayYn("N");
    targetAgenda.setUpdDt(LocalDateTime.now());
      // remove agenda statistics?

    return this.agendaRepository.save(targetAgenda);

  }

  // Increase agenda "likeit"
  public AgendaStatistics increaseLikeIt(Long id) {

    AgendaStatistics agendaStatistics = null;

    try {
      agendaStatistics = this.agendaStatisticsRepository.findByAgendaId(id)
          .orElseThrow(() -> new NoSuchElementException());
    } catch(NoSuchElementException e) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
    }

    // do not increase hit count for the same user
    int likeit = agendaStatistics.getLikeIt();
    log.error("#### " + agendaStatistics.getLikeIt());
    agendaStatistics.setLikeIt(++likeit);
    log.error("#### " + agendaStatistics.getLikeIt());
    // need exception handling
    this.agendaStatisticsRepository.save(agendaStatistics);

    return agendaStatistics;

  }

  // Increase agenda "dislikeit"
  public AgendaStatistics increaseDislikeIt(Long id) {

    AgendaStatistics agendaStatistics = null;

    try {
      agendaStatistics =   this.agendaStatisticsRepository.findByAgendaId(id)
          .orElseThrow(() -> new NoSuchElementException());
    } catch(NoSuchElementException e) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
    }

    // do not increase hit count for the same user
    int dislikeit = agendaStatistics.getDislikeIt();
    log.error("#### " + agendaStatistics.getDislikeIt());
    agendaStatistics.setDislikeIt(++dislikeit);
    log.error("#### " + agendaStatistics.getDislikeIt());
    // need exception handling
    this.agendaStatisticsRepository.save(agendaStatistics);

    return agendaStatistics;

  }

}
