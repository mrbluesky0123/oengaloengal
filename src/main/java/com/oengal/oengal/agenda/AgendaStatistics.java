package com.oengal.oengal.agenda;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="AGENDA_STATISTICS")
@Builder
public class AgendaStatistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long agendaId;

  @Column
  private int hitCount;

  @Column
  private int likeIt;

  @Column
  private int dislikeIt;

  @Column
  private LocalDateTime regDt;

  @Column
  private LocalDateTime updDt;

}
