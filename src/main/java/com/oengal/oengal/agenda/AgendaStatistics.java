package com.oengal.oengal.agenda;


import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name="AGENDA_STATISTICS2")
@Builder
public class AgendaStatistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "statistics_id")
  private Long statisitcsId;

//  @Id
//  @Column(name = "agenda_id")
//  private Long agendaId;

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

  @JsonBackReference
  @OneToOne(mappedBy = "agendaStatistics", cascade = CascadeType.ALL)
  private Agenda agenda;

}
