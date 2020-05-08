package com.oengal.oengal.dislikeit;

import com.oengal.oengal.agenda.Agenda;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DISLIKE_HISTORY")
@ToString
public class DisLikeIt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "userid")
  private String userId;

  @Column(name = "agendaid")
  private Long agendaId;

  @Column(name = "dislikeflag")
  private String dislikeFlag;

  @Column(name = "regdate")
  private LocalDateTime regDate;

}

