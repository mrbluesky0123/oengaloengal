package com.oengal.oengal.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaResponse {

  private Agenda agenda;
  private AgendaStatistics agendaStatistics;

}
