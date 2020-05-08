package com.oengal.oengal.agenda;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="AGENDA_MASTER")
@ToString
@Builder
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agenda_id")
    private Long agendaId;

    @NotNull(message = "Category cannot be null.")
    @NotEmpty(message = "Category cannot be empty.")
    @Column
    private String category;

    @NotNull(message = "Subject cannot be null.")
    @NotEmpty(message = "Subject cannot be empty.")
    @Column
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @NotNull(message = "UserID cannot be null.")
    @NotEmpty(message = "UserID cannot be empty.")
    @Column(name = "user_id")
    private String userId;

    @NotNull(message = "Versus1 cannot be null.")
    @NotEmpty(message = "Versus1 cannot be empty.")
    @Column(name = "versus_1")
    private String versus1;

    @NotNull(message = "Versus2 cannot be null.")
    @NotEmpty(message = "Versus2 cannot be empty.")
    @Column(name = "versus_2")
    private String versus2;

    @NotNull(message = "Contents cannot be null.")
    @NotEmpty(message = "Contents cannot be empty.")
    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(name = "tag_1")
    private String tag1;

    @Column(name = "tag_2")
    private String tag2;

    @Column(name = "tag_3")
    private String tag3;

    @Column(name = "display_yn")
    private String displayYn;
    // Y N
    // CQRS

    @Column(name = "reg_dt")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime regDt;

    @Column(name = "upd_dt")
    private LocalDateTime updDt;


}

