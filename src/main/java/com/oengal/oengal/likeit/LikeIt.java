package com.oengal.oengal.likeit;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="LIKE_HISTORY")
@ToString
@Builder
public class LikeIt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "userid")
  private String userId;

  @Column(name = "agendaid")
  private Long agendaId;

  @Column(name = "likeflag")
  private String likeFlag;

  @Column(name = "regdate")
  @Generated(GenerationTime.INSERT)
  private LocalDateTime regDate;

}
