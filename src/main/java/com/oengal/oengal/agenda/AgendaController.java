package com.oengal.oengal.agenda;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/")
@Slf4j
@Api(value = "AgendaController")
public class AgendaController {

    private AgendaService agendaService;

    @Autowired
    public AgendaController(AgendaService agendaService){
        this.agendaService = agendaService;
    }

    @GetMapping({"/v1/agenda/regdtordered"})
    @ApiOperation(value="논제 카드에 쓰일 정보 요청",
        notes="!!! BASE URL !!!! \n"
            + "http://hostname:port/agenda/api\n"
            + "pageNum, pageSize, sort(정렬기준컬럼)이 쿼리파라미터로 사용되며 sort는 대소문자가 구분된 컬럼명을 사용\n"
            + "sort - regDate, likeit, dislikeit, ...")
    public ResponseEntity<List<AgendaResponse>> getAgendaListOrderByRegDt(
        @RequestParam String pageNum, @RequestParam String pageSize){

        List<AgendaResponse> agendaResponseList = this.agendaService.getAgendaList(Integer.parseInt(pageNum),
            Integer.parseInt(pageSize), "regDt");
        log.error("cccccccontroller");
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponseList);

    }

    @GetMapping({"/v1/agenda/likeitordered"})
    public ResponseEntity<List<AgendaResponse>> getAgendaListOrderByLikeit(
        @RequestParam String pageNum, @RequestParam String pageSize){

        List<AgendaResponse> agendaResponseList = this.agendaService.getAgendaList(Integer.parseInt(pageNum),
            Integer.parseInt(pageSize), "agendaStatistics.likeIt");
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponseList);

    }

    @GetMapping({"/v1/agenda/{agendaId}"})
    @ApiOperation(value="하나의 논제 정보 요청",
            notes="논제 id로 하나의 논제에 대한 정보를 요청한다")
    @UserIdFromCookie
    public ResponseEntity<AgendaResponse> getAgenda(String userId, @PathVariable Long agendaId){

        log.error(userId);
        AgendaResponse agendaResponse = this.agendaService.getAgenda(agendaId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);

    }

    @PostMapping({"/v1/agenda"})
    @ApiOperation(value="논제 작성",
            notes="category(카테고리), register(유저ID), subject(제목), versus1(대상1), versus2(대상2), "
                    + "contents(내용) 는 필수값(empty값도 허용하지 않음)")
    public ResponseEntity<Agenda> requestAgenda(@RequestBody @Valid Agenda agenda) {
//        Scanner s = new Scanner(System.in);
        log.error("@@@@@@ [CONTROLLER] Received request.");
        Agenda newAgenda = this.agendaService.postAgenda(agenda);
        log.error("@@@@@@ [CONTROLLER] Before return response.");
//        s.next();
        log.error("^^^^^^^^" + agenda.getUserId().toString());
        return ResponseEntity.status(HttpStatus.OK).body(newAgenda);

    }

    @PutMapping({"/v1/agenda"})
    @ApiOperation(value="논제 수정",
        notes="category(카테고리), register(유저ID), subject(제목), versus1(대상1), versus2(대상2), "
            + "contents(내용)는 필수값(empty값도 허용하지 않음)" )
    public ResponseEntity<Agenda> modifyAgenda(@RequestBody @Valid Agenda agenda){
//        Scanner s = new Scanner(System.in);
        Agenda newAgenda = this.agendaService.modifyAgenda(agenda);
        newAgenda.setTag1("123123");
//        s.next();
        return ResponseEntity.status(HttpStatus.OK).body(newAgenda);

    }

    @DeleteMapping({"/v1/agenda/{id}"})
    @ApiOperation(value="논제 삭제", notes="논제 id로 하나의 논제를 삭제한다.")
    public ResponseEntity<Agenda> deleteAgenda(@PathVariable Long id){

        Agenda agenda = this.agendaService.deleteAgenda(id);
        return ResponseEntity.status(HttpStatus.OK).body(agenda);

    }

    @PutMapping({"/v1/agenda/likeitinc/{agendaId}"})
    @ApiOperation(value="논제 좋아요 증가", notes="논제 id로 하나의 논제 '좋아요'를 1증가 시킨다.")
    public ResponseEntity<AgendaResponse> increaseLikeit(@CookieValue(value = "userid", defaultValue = "FFF") String userId, @PathVariable Long agendaId){

        AgendaResponse agendaResponse = this.agendaService.increaseLikeIt(agendaId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);

    }

    @PutMapping({"/v1/agenda/likeitdec/{agendaId}"})
    @ApiOperation(value="논제 좋아요 증가", notes="논제 id로 하나의 논제 '좋아요'를 1증가 시킨다.")
    public ResponseEntity<AgendaResponse> decreaseLikeit(@CookieValue(value = "userid", defaultValue = "N/A") String userId, @PathVariable Long agendaId){

        AgendaResponse agendaResponse = this.agendaService.decreaseLikeIt(agendaId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);

    }

    @PutMapping({"/v1/agenda/dislikeit/{id}"})
    @ApiOperation(value="논제 싫어요 증가", notes="논제 id로 하나의 논제 '싫어요'를 1증가 시킨다.")
    public ResponseEntity<AgendaStatistics> increaseDislikeit(@PathVariable Long id){

        AgendaStatistics newAgendaStatistics = this.agendaService.increaseDislikeIt(id);
        return ResponseEntity.status(HttpStatus.OK).body(newAgendaStatistics);

    }

}
