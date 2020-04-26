package com.oengal.oengal.agenda;

import com.oengal.oengal.common.BaseResponse;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDetailBaseResponse extends BaseResponse {

  private Long id;
  private String category;
  private String subject;
  private String thumbnail;
  private String register;
  private String versus1;
  private String versus2;
  private String contents;
  private int hitCount;
  private int likeIt;
  private int dislikeIt;
  private String tag1;
  private String tag2;
  private String tag3;
  private Timestamp regDate;
  private Timestamp updDate;

}
