package com.oengal.oengal.agenda;

import com.oengal.oengal.common.BaseResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AgendaCard extends BaseResponse {

  private List<Agenda> agendas;

}
