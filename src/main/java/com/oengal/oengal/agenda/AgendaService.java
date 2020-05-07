package com.oengal.oengal.agenda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class AgendaService {

    private AgendaRepository agendaRepository;
    private AgendaStatisticsRepository agendaStatisticsRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AgendaService(AgendaRepository agendaRepository, AgendaStatisticsRepository agendaStatisticsRepository) {

        this.agendaRepository = agendaRepository;
        this.agendaStatisticsRepository = agendaStatisticsRepository;
        this.modelMapper = new ModelMapper();

    }


    // Select agenda list
    public List<Agenda> getAgendaList(int pageNum, int pageSize, String sort) {

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(sort).descending());
        Page<Agenda> agendaPage = agendaRepository.findByDisplayYn("Y", pageRequest);
        if(!agendaPage.hasContent()) {
            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No agendas.");
        }
        List<Agenda> agendaList = agendaPage.getContent();

        return agendaList;

    }


    // Select single agenda
    public Agenda getAgenda(Long id) {

        Agenda agenda = null;
        AgendaStatistics agendaStatistics = null;

        try {
            agenda = this.agendaRepository.findByAgendaIdAndDisplayYn(id, "Y")
                .orElseThrow(() -> new NoSuchElementException());
            agendaStatistics = this.agendaStatisticsRepository.findByAgendaId(id)
                .orElseThrow(() -> new NoSuchElementException());
        } catch(NoSuchElementException e) {
            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
        }

        int hitCount = agendaStatistics.getHitCount();
        log.error("#### " + agendaStatistics.getHitCount());
        agendaStatistics.setHitCount(++hitCount);
        // need exception handling
        log.error("#### " + agendaStatistics.getHitCount());
        this.agendaStatisticsRepository.save(agendaStatistics);

        return agenda;

    }

    // Post agenda
    public Agenda postAgenda(Agenda agenda) {

        Agenda newAgenda = null;
        agenda.setDisplayYn("Y");

        try {

            newAgenda = this.agendaRepository.save(agenda);

        } catch (DataAccessException e) {
            // need more detail
            throw new AgendaException(HttpStatus.INTERNAL_SERVER_ERROR, -102, "Agenda save error!");
        }

        AgendaStatistics newAgendaStatistics = AgendaStatistics.builder()
                                                            .agendaId(newAgenda.getAgendaId())
                                                            .likeIt(0)
                                                            .dislikeIt(0)
                                                            .build();
        try {

            newAgendaStatistics = this.agendaStatisticsRepository.save(newAgendaStatistics);

        } catch (DataAccessException e) {
            // need more detail
            throw new AgendaException(HttpStatus.INTERNAL_SERVER_ERROR, -102,
                                                    "Agenda statistics save error!");
        }


        return newAgenda;

    }

    // Modify agenda
    public Agenda modifyAgenda(Agenda modifiedAgenda) {

        if(modifiedAgenda.getAgendaId() == null) {
                throw new AgendaException(HttpStatus.BAD_REQUEST, -101, "Id cannot be null.");
            }

            Agenda targetAgenda = null;

            try {

                targetAgenda = this.agendaRepository.findByAgendaIdAndDisplayYn(
                                                        modifiedAgenda.getAgendaId(), "Y")
                                                    .orElseThrow(() -> new NoSuchElementException());
                targetAgenda.setCategory(modifiedAgenda.getCategory());
                targetAgenda.setSubject(modifiedAgenda.getSubject());
                targetAgenda.setContents(modifiedAgenda.getContents());
                targetAgenda.setTag1(modifiedAgenda.getTag1());
                targetAgenda.setTag2(modifiedAgenda.getTag2());
                targetAgenda.setTag3(modifiedAgenda.getTag3());
                targetAgenda.setUpdDt(LocalDateTime.now());

        } catch(NoSuchElementException e) {

            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");

        }

        Agenda resultAgenda = null;

        try {

            resultAgenda = this.agendaRepository.save(targetAgenda);

        } catch (DataAccessException e) {
            // need more detail
            throw new AgendaException(HttpStatus.INTERNAL_SERVER_ERROR, -102, e.getLocalizedMessage());
        }

        return resultAgenda;

    }

    // Delete agenda
    public Agenda deleteAgenda(Long id) {

        Agenda targetAgenda = null;

        try {

            targetAgenda = this.agendaRepository.findByAgendaIdAndDisplayYn(id, "Y")
                .orElseThrow(() -> new NoSuchElementException());
            targetAgenda.setDisplayYn("N");
            targetAgenda.setUpdDt(LocalDateTime.now());
            // remove agenda statistics

        } catch(NoSuchElementException e) {

            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");

        }

        Agenda resultAgenda = null;

        try {

            resultAgenda = this.agendaRepository.save(targetAgenda);

        } catch (DataAccessException e) {
            // need more detail
            throw new AgendaException(HttpStatus.INTERNAL_SERVER_ERROR, -102, e.getLocalizedMessage());
        }

        return resultAgenda;

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
    public Agenda increaseDislikeIt(Long id) {

        Agenda agenda = null;

        try {
            agenda = this.agendaRepository.findByIdAndShowable(id, "Y")
                .orElseThrow(() -> new NoSuchElementException());
        } catch(NoSuchElementException e) {
            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
        }

        // do not increase hit count for the same user
        int dislikeit = agenda.getDislikeIt();
        log.error("#### " + agenda.getDislikeIt());
        agenda.setDislikeIt(++dislikeit);
        log.error("#### " + agenda.getDislikeIt());
        // need exception handling
        this.agendaRepository.save(agenda);

        return agenda;

    }

}
