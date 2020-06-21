package com.oengal.oengal.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaResponse {

  private Agenda agenda; // statistics
  private boolean isLiked;  //
  private boolean isDisliked;

}
