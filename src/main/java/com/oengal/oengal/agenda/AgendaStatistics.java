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
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="AGENDA_STATISTICS")
@Builder
public class AgendaStatistics {

  @Id
  @Column(name = "agenda_id")
  private Long agendaId;

  @Column(name = "hit_count")
  private int hitCount;

  @Column(name = "like_it")
  private int likeIt;

  @Column(name = "dislike_it")
  private int dislikeIt;

  @Column(name = "reg_dt")
  @Generated(GenerationTime.INSERT)
  private LocalDateTime regDt;

  @Column(name = "upd_dt")
  private LocalDateTime updDt;

}
