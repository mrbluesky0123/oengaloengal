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
    private ModelMapper modelMapper;

    @Autowired
    public AgendaService(AgendaRepository agendaRepository) {

        this.agendaRepository = agendaRepository;
        this.modelMapper = new ModelMapper();

    }


    // Select agenda list
    public List<Agenda> getAgendaList(int pageNum, int pageSize, String sort) {

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(sort).descending());
        Page<Agenda> agendaPage = agendaRepository.findByShowable("Y", pageRequest);
        if(!agendaPage.hasContent()) {
            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No agendas.");
        }
        List<Agenda> agendaList = agendaPage.getContent();

        return agendaList;

    }


    // Select single agenda
    public Agenda getAgenda(Long id) {

        Agenda agenda = null;

        try {
            agenda = this.agendaRepository.findByIdAndShowable(id, "Y")
                .orElseThrow(() -> new NoSuchElementException());
        } catch(NoSuchElementException e) {
            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
        }

        int hitCount = agenda.getHitCount();
        log.error("#### " + agenda.getHitCount());
        agenda.setHitCount(++hitCount);
        // need exception handling
        log.error("#### " + agenda.getHitCount());
        this.agendaRepository.save(agenda);

        return agenda;

    }

    public Agenda postAgenda(Agenda agenda) {

        Agenda newAgenda = null;
        agenda.setDisplayYn("Y");

        try {
            newAgenda = this.agendaRepository.save(agenda);
        } catch (DataAccessException e) {
            // need more detail
            throw new AgendaException(HttpStatus.INTERNAL_SERVER_ERROR, -102, "Save error!");
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

                targetAgenda = this.agendaRepository.findByIdAndShowable(modifiedAgenda.getAgendaId(), "Y")
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

            targetAgenda = this.agendaRepository.findByIdAndShowable(id, "Y")
                .orElseThrow(() -> new NoSuchElementException());
            targetAgenda.setDisplayYn("N");
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

    // Increase agenda "likeit"
    /*public Agenda increaseLikeIt(Long id) {

        Agenda agenda = null;

        try {
            agenda = this.agendaRepository.findByIdAndShowable(id, "Y")
                .orElseThrow(() -> new NoSuchElementException());
        } catch(NoSuchElementException e) {
            throw new AgendaException(HttpStatus.BAD_REQUEST, -100, "No such agenda exists.");
        }

        // do not increase hit count for the same user
        int likeit = agenda.getLikeIt();
        log.error("#### " + agenda.getLikeIt());
        agenda.setLikeIt(++likeit);
        log.error("#### " + agenda.getLikeIt());
        // need exception handling
        this.agendaRepository.save(agenda);

        return agenda;

    }*/

    // Increase agenda "dislikeit"
    /*public Agenda increaseDislikeIt(Long id) {

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

    }*/

}
