package com.oengal.oengal.agenda;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Getter
@Setter
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

    @NotNull(message = "'category' cannot be null.")
    @NotEmpty(message = "'category' cannot be empty.")
    @Column
    private String category;

    @NotNull(message = "'subject' cannot be null.")
    @NotEmpty(message = "'subject' cannot be empty.")
    @Column
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @NotNull(message = "'userId' cannot be null.")
    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "'nickname' cannot be null.")
    @NotEmpty(message = "'nickname' cannot be empty.")
    @Column(name = "nickname")
    private String nickname;

    @NotNull(message = "'versus1' cannot be null.")
    @NotEmpty(message = "'versus1' cannot be empty.")
    @Column(name = "versus_1")
    private String versus1;

    @NotNull(message = "'versus2' cannot be null.")
    @NotEmpty(message = "'versus2' cannot be empty.")
    @Column(name = "versus_2")
    private String versus2;

    @NotNull(message = "'contents' cannot be null.")
    @NotEmpty(message = "'contents' cannot be empty.")
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

    @Column(name = "reg_dt")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime regDt;

    @Column(name = "upd_dt")
    private LocalDateTime updDt;


}

