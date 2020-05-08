package com.oengal.oengal.agenda;


import com.oengal.oengal.common.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @GetMapping({"/v1/agenda"})
    @ApiOperation(value="논제 카드에 쓰일 정보 요청",
        notes="!!! BASE URL !!!! \n"
            + "http://hostname:port/agenda/api\n"
            + "pageNum, pageSize, sort(정렬기준컬럼)이 쿼리파라미터로 사용되며 sort는 대소문자가 구분된 컬럼명을 사용\n"
            + "sort - regDate, likeit, dislikeit, ...")
    public ResponseEntity<List<AgendaResponse>> getAgendaList(
        @RequestParam String pageNum, @RequestParam String pageSize, @RequestParam String sort){

        List<AgendaResponse> agendaResponseListList = this.agendaService.getAgendaList(Integer.parseInt(pageNum),
            Integer.parseInt(pageSize), sort);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponseListList);

    }

    @GetMapping({"/v1/agenda/{id}"})
    @ApiOperation(value="하나의 논제 정보 요청",
        notes="논제 id로 하나의 논제에 대한 정보를 요청한다")
    public ResponseEntity<AgendaResponse> getAgenda(@PathVariable Long id){

        AgendaResponse agendaResponse = this.agendaService.getAgenda(id);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);

    }

    @PostMapping({"/v1/agenda"})
    @ApiOperation(value="논제 작성",
        notes="category(카테고리), register(유저ID), subject(제목), versus1(대상1), versus2(대상2), "
            + "contents(내용) 는 필수값(empty값도 허용하지 않음)")
    public ResponseEntity<AgendaResponse> requestAgenda(@RequestBody @Valid Agenda agenda) {

        AgendaResponse newAgendaResponse = this.agendaService.postAgenda(agenda);
        return ResponseEntity.status(HttpStatus.OK).body(newAgendaResponse);

    }

    @PutMapping({"/v1/agenda"})
    @ApiOperation(value="논제 수정",
        notes="category(카테고리), register(유저ID), subject(제목), versus1(대상1), versus2(대상2), "
            + "contents(내용)는 필수값(empty값도 허용하지 않음)" )
    public ResponseEntity<Agenda> modifyAgenda(@RequestBody @Valid Agenda agenda){

        Agenda newAgenda = this.agendaService.modifyAgenda(agenda);
        return ResponseEntity.status(HttpStatus.OK).body(newAgenda);

    }

    @DeleteMapping({"/v1/agenda/{id}"})
    @ApiOperation(value="논제 삭제", notes="논제 id로 하나의 논제를 삭제한다.")
    public ResponseEntity<Agenda> deleteAgenda(@PathVariable Long id){

        Agenda agenda = this.agendaService.deleteAgenda(id);
        return ResponseEntity.status(HttpStatus.OK).body(agenda);

    }

    @PutMapping({"/v1/agenda/likeit/{id}"})
    @ApiOperation(value="논제 좋아요 증가", notes="논제 id로 하나의 논제 '좋아요'를 1증가 시킨다.")
    public ResponseEntity<AgendaStatistics> increaseLikeit(@PathVariable Long id){

        AgendaStatistics newAgendaStatistics = this.agendaService.increaseLikeIt(id);
        return ResponseEntity.status(HttpStatus.OK).body(newAgendaStatistics);

    }

    @PutMapping({"/v1/agenda/dislikeit/{id}"})
    @ApiOperation(value="논제 싫어요 증가", notes="논제 id로 하나의 논제 '싫어요'를 1증가 시킨다.")
    public ResponseEntity<AgendaStatistics> increaseDislikeit(@PathVariable Long id){

        AgendaStatistics newAgendaStatistics = this.agendaService.increaseDislikeIt(id);
        return ResponseEntity.status(HttpStatus.OK).body(newAgendaStatistics);

    }

    @ExceptionHandler(AgendaException.class)
    public ResponseEntity<ErrorResponse> returnAgendaException(AgendaException agendaException) {

        ErrorResponse errorResponse = new ErrorResponse
            .ErrorResponseBuilder()
            .bizErrorCode(agendaException.getCode())
            .message(agendaException.getMessage())
            .build();

        return ResponseEntity.status(agendaException.getHttpStatus()).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> returnInvalidInputException(MethodArgumentNotValidException methodArgumentNotValidException) {

        ErrorResponse errorResponse = new ErrorResponse
            .ErrorResponseBuilder()
            .bizErrorCode(-101)
            .message(methodArgumentNotValidException.getBindingResult().getFieldError().
                getDefaultMessage())
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }

}
