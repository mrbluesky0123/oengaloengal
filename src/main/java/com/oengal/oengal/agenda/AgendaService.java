package com.oengal.oengal.agenda;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import java.util.Scanner;
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

    //
    Page<Agenda> agendaPage = agendaRepository.findByDisplayYn("Y", pageRequest);
    if(!agendaPage.hasContent()) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No agendas.");
    }
    List<Agenda> agendaList = agendaPage.getContent();

    // Make reponse with agenda, agenda statistics
    for(Agenda agenda: agendaList){
      // 지연 로딩 확인
      log.error("##### FUCK");
      AgendaStatistics agendaStatistics = this.agendaStatisticsRepository
                                                .findById(agenda.getAgendaId()) // Optional<Agenda>
                                                .orElse(AgendaStatistics.builder()
//                                                          .agendaId(agenda.getAgendaId())
                                                          .hitCount(0)
                                                          .likeIt(0)
                                                          .dislikeIt(0)
                                                          .build()
                                                );
      log.error("##### FUCK2");
      AgendaResponse agendaResponse = new AgendaResponse(agenda, agendaStatistics);
      agendaResponseList.add(agendaResponse);

    }

    return agendaResponseList;

  }


  // Select single agenda
  public Agenda getAgenda(Long id) {

    Agenda agenda = null;
    AgendaStatistics agendaStatistics = null;


    // Get single agenda
    agenda = this.agendaRepository.findByAgendaIdAndDisplayYn(id, "Y")
        .orElseThrow(() -> new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists."));

    // Increase hit count
    int hitCount = agenda.getAgendaStatistics().getHitCount();
    agenda.getAgendaStatistics().setHitCount(++hitCount);
//    this.agendaStatisticsRepository.save(agendaStatistics);


    return agenda;

  }

  // Post agenda

  public Agenda postAgenda(Agenda agenda) {

    agenda.setDisplayYn("Y");
    // Post agenda
    // Need transaction operation

//    Agenda newAgenda = this.agendaRepository.save(agenda);
    Agenda newAgenda = this.agendaRepository.save(agenda);
    AgendaStatistics newAgendaStatistics = AgendaStatistics.builder()
                                                          .likeIt(0)
                                                          .dislikeIt(0)
                                                          .build();
    agenda.setAgendaStatistics(newAgendaStatistics);

//    newAgendaStatistics = this.agendaStatisticsRepository.save(newAgendaStatistics);
//    return new AgendaResponse(newAgenda, null);
    return newAgenda;

  }

  // Modify agenda
  public Agenda modifyAgenda(Agenda modifiedAgenda) {

    if(modifiedAgenda.getAgendaId() == null) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -101, "Id cannot be null.");
    }

    // Need to Make agenda immutable
    Agenda targetAgenda = this.agendaRepository.findByAgendaIdAndDisplayYn(
          modifiedAgenda.getAgendaId(), "Y")
          .orElseThrow(() -> new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists."));
    Scanner s = new Scanner(System.in);
    targetAgenda.setCategory(modifiedAgenda.getCategory());
    targetAgenda.setSubject(modifiedAgenda.getSubject());
    targetAgenda.setContents(modifiedAgenda.getContents());
    targetAgenda.setTag1(modifiedAgenda.getTag1());
    targetAgenda.setTag2(modifiedAgenda.getTag2());
    targetAgenda.setTag3(modifiedAgenda.getTag3());
    targetAgenda.setUpdDt(LocalDateTime.now());
    s.next();
    return targetAgenda;

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
      agendaStatistics = this.agendaStatisticsRepository.findById(id)
          .orElseThrow(() -> new NoSuchElementException());
    } catch(NoSuchElementException e) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
    }

    // do not increase hit count for the same user
    int likeit = agendaStatistics.getLikeIt();
    agendaStatistics.setLikeIt(++likeit);
    // need exception handling
    this.agendaStatisticsRepository.save(agendaStatistics);

    return agendaStatistics;

  }

  // Increase agenda "dislikeit"
  public AgendaStatistics increaseDislikeIt(Long id) {

    AgendaStatistics agendaStatistics = null;

    try {
      agendaStatistics = this.agendaStatisticsRepository.findById(id)
          .orElseThrow(() -> new NoSuchElementException());
    } catch(NoSuchElementException e) {
      throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
    }

    // do not increase hit count for the same user
    int dislikeit = agendaStatistics.getDislikeIt();
    agendaStatistics.setDislikeIt(++dislikeit);
    // need exception handling
    this.agendaStatisticsRepository.save(agendaStatistics);

    return agendaStatistics;

  }

}
