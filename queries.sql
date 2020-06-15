
DESC COMMENT_MASTER;
DESC BOARD_MASTER;
DESC BOARD_CONTENTS;
USE oengal;

SELECT * FROM BOARD_CONTENTS;
SELECT * FROM COMMENT_MASTER;

ALTER TABLE COMMENT_MASTER CHANGE contents comment varchar(300) not null;
# ALTER TABLE COMMENT_MASTER CHANGE upd_dt upddate datetime;


INSERT INTO BOARD_CONTENTS(id, contents)
 VALUES(1, '10억억억소리나는 자산가 김태희 vs 연봉 3000 오나미... 밸붕 ㅇㅈ하는각임???');


INSERT INTO COMMENT_MASTER(brdid, comment, register, likeit, dislikeit, regdate)
VALUES (1, 'ㅇㅇ ㅇㅈ.... 쌉인정..', '나그네', 0, 0, now());
INSERT INTO COMMENT_MASTER(brdid, comment, register, likeit, dislikeit, regdate)
VALUES (1, '미쳤냐??', 'ㅇㅇ', 0, 0, now());

show tables;


DESC BOARD_CONTENTS;


Desc BOARD_CONTENTS;
SELECT * FROM BOARD_MASTER;
SELECT * FROM BOARD_CONTENTS;
Desc BOARD_MASTER;

SHOW CREATE TABLE BOARD_MASTER;
SHOW CREATE TABLE BOARD_CONTENTS;

ALTER TABLE BOARD_MASTER CHANGE `id` `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제ID';
ALTER TABLE BOARD_MASTER CHANGE `regdate` `regdate` datetime DEFAULT now() NOT NULL COMMENT '등록일';
ALTER TABLE BOARD_MASTER ADD COLUMN hitcount int(10) unsigned DEFAULT NULL COMMENT '조회수';
ALTER TABLE BOARD_MASTER ADD COLUMN showable varchar(1) DEFAULT 'Y' COMMENT '표시여부(삭제시N)' AFTER `tag3`;

ALTER TABLE BOARD_MASTER CHANGE `versus_2` `versus2` varchar(400);
ALTER TABLE BOARD_CONTENTS MODIFY COLUMN id int(11) unsigned zerofill COMMENT '게시글ID';
ALTER TABLE BOARD_CONTENTS MODIFY COLUMN contents text COMMENT '내용';

DESC BOARD_MASTER;

show variables like 'lower_case_table_names';

CREATE TABLE `BOARD_MASTER` (
                                `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제ID',
                                `category` varchar(1000) NOT NULL COMMENT '카테고리',
                                `subject` varchar(1000) NOT NULL COMMENT '제목',
                                `thumbnail` text COMMENT '썸네일',
                                `register` varchar(20) NOT NULL COMMENT '작성자',
                                `versus1` varchar(400) NOT NULL COMMENT '1번대상',
                                `versus2` varchar(400) NOT NULL COMMENT '2번대상',
                                `contents` text COMMENT '내용',
                                `hitcount` int(10) unsigned DEFAULT NULL COMMENT '조회수',
                                `likeit` int(10) unsigned DEFAULT NULL COMMENT '좋아요',
                                `dislikeit` int(10) unsigned DEFAULT NULL COMMENT '싫어요',
                                `tag1` varchar(20) COMMENT '태그1',
                                `tag2` varchar(20) COMMENT '태그2',
                                `tag3` varchar(20) COMMENT '태그3',
                                `regdate` datetime NOT NULL COMMENT '등록일',
                                `upddate` datetime DEFAULT NULL COMMENT '수정일',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

DROP TABLE `BOARD_MASTER`;

INSERT INTO BOARD_MASTER
(`subject`, `thumbnail`, `register`, `versues_1`, `versues_2`, `contents`, `likeit`, `dislikeit`, `regdate`, `upddate`)
VALUES ('테스트용 제목데스네????',
        '',
        'Rain',
        '100억 건물주 김태희',
        '연봉 5000만원 오나미',
        '벨붕 인저엉어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어어엉?',
        0,
        0,
        now(),
        now());

);

UPDATE BOARD_MASTER SET thumbnail = 'https://totozzang.com/data/editor/2004/b832ce53504e0162cf865053bdd7e04a_1587540903_8736.jpg'
WHERE id = 2;
SELECT * FROM BOARD_MASTER;
CREATE TABLE `BOARD_MASTER` (
                                `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제ID',
                                `subject` varchar(1000) NOT NULL COMMENT '제목',
                                `thumbnail` text COMMENT '썸네일',
                                `register` varchar(20) NOT NULL COMMENT '작성자',
                                `versus1` varchar(400) DEFAULT NULL,
                                `versus2` varchar(400) DEFAULT NULL,
                                `contents` text COMMENT '내용',
                                `likeit` int(10) unsigned DEFAULT NULL COMMENT '좋아요',
                                `dislikeit` int(10) unsigned DEFAULT NULL COMMENT '싫어요',
                                `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                `upddate` datetime DEFAULT NULL COMMENT '수정일',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;



INSERT INTO BOARD_MASTER(`category`,
                         `subject`,
                         `thumbnail`,
                         `register`,
                         `versus1`,
                         `versus2`,
                         `contents`,
                         `hitcount`,
                         `likeit`,
                         `dislikeit`,
                         `tag1`,
                         `tag2`,
                         `tag3`,
                         `regdate`,
                         `upddate`)
VALUES('잡담',
       '머가 더 좋냐',
       'https://thumb.named.com/normal/resize/origin/file/photo/editor/2004/7459a9b865262410da65919550378424_JwoX3qmNUis21tS.jpg',
       '한승엽',
       '가슴',
       '다리',
       '난 어렸을땐 가슴이었는데 나이 들수록 다리...',
       0,
       0,
       0,
       '섹시',
       null,
       null,
       now(),
       now()
       );



CREATE TABLE `BOARD_MASTER` (
                                `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제ID',
                                `category` varchar(1000) NOT NULL COMMENT '카테고리',
                                `subject` varchar(1000) NOT NULL COMMENT '제목',
                                `thumbnail` text COMMENT '썸네일',
                                `register` varchar(20) NOT NULL COMMENT '작성자',
                                `versus1` varchar(400) NOT NULL COMMENT '1번대상',
                                `versus2` varchar(400) NOT NULL COMMENT '2번대상',
                                `contents` text COMMENT '내용',
                                `hitcount` int(10) unsigned DEFAULT NULL COMMENT '조회수',
                                `likeit` int(10) unsigned DEFAULT NULL COMMENT '좋아요',
                                `dislikeit` int(10) unsigned DEFAULT NULL COMMENT '싫어요',
                                `tag1` varchar(20) DEFAULT NULL COMMENT '태그1',
                                `tag2` varchar(20) DEFAULT NULL COMMENT '태그2',
                                `tag3` varchar(20) DEFAULT NULL COMMENT '태그3',
                                `showable` varchar(1) DEFAULT 'Y' COMMENT '표시여부(삭제시N)',
                                `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                `upddate` datetime DEFAULT NULL COMMENT '수정일',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4
;


USE ApproveDB;
show tables;
SELECT * FROM COM_PRFBAT_MST;
SHOW DATABASES;




CREATE TABLE `LIKE_HISTORY` (
                                `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                `userid` varchar(20) NOT NULL COMMENT '작성자',
                                `agendaid` bigint(20) unsigned zerofill NOT NULL COMMENT '논제ID',
                                ``
                                `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4
;

DESC LIKE_HISTORY;

CREATE TABLE `DISLIKE_HISTORY` (
                                `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                `userid` varchar(20) NOT NULL COMMENT '작성자',
                                `agendaid` bigint(20) unsigned zerofill NOT NULL COMMENT '논제ID',
                                ``
                                `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4
;



show tables;

DESC COMMENT_MASTER;

SELECT * FROM BOARD_MASTER;





SHOW CREATE TABLE LIKE_HISTORY;

CREATE TABLE `DISLIKE_HISTORY` (
                                   `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `userid` varchar(20) NOT NULL COMMENT '작성자',
                                   `agendaid` bigint(20) unsigned zerofill NOT NULL COMMENT '논제ID',
                                   `dislikeflag` varchar(1) NOT NULL COMMENT '좋아요여부',
                                   `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;



SELECT * FROM COMMENT_MASTER;

DROP TABLE `DISLIKE_HISTORY`;

DESC BOARD_MASTER;




SHOW CREATE TABLE BOARD_MASTER;
DESC AGENDA_MASTER;

CREATE TABLE `BOARD_MASTER` (
                                `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제ID',
                                `category` varchar(1000) NOT NULL COMMENT '카테고리',
                                `subject` varchar(1000) NOT NULL COMMENT '제목',
                                `thumbnail` text COMMENT '썸네일',
                                `register` varchar(20) NOT NULL COMMENT '작성자',
                                `versus1` varchar(400) NOT NULL COMMENT '1번대상',
                                `versus2` varchar(400) NOT NULL COMMENT '2번대상',
                                `contents` text COMMENT '내용',
                                `hitcount` int(10) unsigned DEFAULT NULL COMMENT '조회수',
                                `likeit` int(10) unsigned DEFAULT NULL COMMENT '좋아요',
                                `dislikeit` int(10) unsigned DEFAULT NULL COMMENT '싫어요',
                                `tag1` varchar(20) DEFAULT NULL COMMENT '태그1',
                                `tag2` varchar(20) DEFAULT NULL COMMENT '태그2',
                                `tag3` varchar(20) DEFAULT NULL COMMENT '태그3',
                                `showable` varchar(1) DEFAULT 'Y' COMMENT '표시여부(삭제시N)',
                                `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                `upddate` datetime DEFAULT NULL COMMENT '수정일',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4;


ALTER TABLE `BOARD_MASTER` CHANGE `id` `agenda_id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제ID';
ALTER TABLE `BOARD_MASTER` CHANGE `versus1` `versus_1` varchar(400) NOT NULL COMMENT '1번대상';
ALTER TABLE `BOARD_MASTER` CHANGE `versus2` `versus_2` varchar(400) NOT NULL COMMENT '2번대상';
ALTER TABLE `BOARD_MASTER` CHANGE `tag1` `tag_1` varchar(20) DEFAULT NULL COMMENT '태그1';
ALTER TABLE `BOARD_MASTER` CHANGE `tag2` `tag_2` varchar(20) DEFAULT NULL COMMENT '태그2';
ALTER TABLE `BOARD_MASTER` CHANGE `tag3` `tag_3` varchar(20) DEFAULT NULL COMMENT '태그3';
ALTER TABLE `BOARD_MASTER` CHANGE `showable` `display_yn` varchar(1) DEFAULT 'Y' COMMENT '표시여부(삭제시N)';
ALTER TABLE `BOARD_MASTER` CHANGE `regdate` `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일';
ALTER TABLE `BOARD_MASTER` CHANGE `upddate` `upd_dt` datetime DEFAULT NULL COMMENT '수정일';
ALTER TABLE `BOARD_MASTER` CHANGE `member_id` `user_id` varchar(20) NOT NULL COMMENT '작성자';

ALTER TABLE `BOARD_MASTER` DROP `hitcount`;
ALTER TABLE `BOARD_MASTER` DROP `likeit`;
ALTER TABLE `BOARD_MASTER` DROP `dislikeit`;



CREATE TABLE `AGENDA_STATISTICS` (
                                     `agenda_id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제ID',
                                     `hit_count` int(10) unsigned DEFAULT NULL COMMENT '조회수',
                                     `like_it` int(10) unsigned DEFAULT NULL COMMENT '좋아요수',
                                     `dislike_it` int(10) unsigned DEFAULT NULL COMMENT '싫어요수',
                                     `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                     `upd_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
                                     PRIMARY KEY (`agenda_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `AGENDA_STATISTICS2` (
                                    `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '논제통계ID',
                                     `agenda_id` bigint(20) unsigned zerofill NOT NULL COMMENT '논제ID',
                                     `hit_count` int(10) unsigned DEFAULT NULL COMMENT '조회수',
                                     `like_it` int(10) unsigned DEFAULT NULL COMMENT '좋아요수',
                                     `dislike_it` int(10) unsigned DEFAULT NULL COMMENT '싫어요수',
                                     `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                     `upd_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


RENAME TABLE `BOARD_MASTER` TO `AGENDA_MASTER`;
DROP TABLE `AGENDA_STATISTICS`;
show tables;

ALTER TABLE AGENDA_STATISTICS CHANGE `upd_dt` `upd_dt` datetime DEFAULT NULL COMMENT '수정일';

DESC `AGENDA_STATISTICS`;

DESC AGENDA_MASTER;

SELECT * FROM AGENDA_MASTER;
SELECT * FROM AGENDA_STATISTICS;
DESC LIKE_HISTORY;

TRUNCATE AGENDA_MASTER;
ALTER TABLE `AGENDA_MASTER` ADD COLUMN `nickname` varchar() DEFAULT NULL COMMENT '수정일';

ALTER TABLE AGENDA_MASTER CHANGE `user_id` `nickname` varchar(20) NOT NULL COMMENT '작성자닉네임';
ALTER TABLE AGENDA_MASTER ADD COLUMN `user_id` bigint(10) unsigned zerofill NOT NULL COMMENT '작성자ID' AFTER `thumbnail`;
ALTER TABLE AGENDA_MASTER ADD COLUMN `statistics_id` bigint(20) unsigned zerofill NOT NULL COMMENT '논제통계ID'; FOREIGN KEY (필드이름)
ALTER TABLE AGENDA_MASTER ADD FOREIGN KEY (`statistics_id`) REFERENCES `AGENDA_STATISTICS2`(`statistics_id`);

REFERENCES 테이블이름 (필드이름);
ALTER TABLE AGENDA_MASTER DROP `statistics_id`;
SELECT * FROM AGENDA_MASTER;
SELECT * FROM AGENDA_STATISTICS;
DESC AGENDA_MASTER;
DESC AGENDA_STATISTICS2;

SELECT * FROM AGENDA_STATISTICS; WHERE agenda_id = 85;
ALTER TABLE AGENDA_STATISTICS ADD COLUMN `agenda_id` bigint(20) unsigned zerofill NOT NULL COMMENT '논제ID' AFTER `id`;
ALTER TABLE AGENDA_STATISTICS2 CHANGE `id` `statistics_id` bigint(20) unsigned zerofill NOT NULL COMMENT '논제통계ID';
ALTER TABLE AGENDA_STATISTICS drop `agenda_id`;
DESC LIKE_HISTORY;

AGENDA_TONGYE;



SHOW TABLES;
select
       agendastat0_.agenda_id as agenda_i7_1_1_,
       agendastat0_.dislike_it as dislike_2_1_1_,
       agendastat0_.hit_count as hit_coun3_1_1_,
       agendastat0_.like_it as like_it4_1_1_,
       agendastat0_.reg_dt as reg_dt5_1_1_,
       agendastat0_.upd_dt as upd_dt6_1_1_,
       agenda1_.agenda_id as agenda_i1_0_0_,
       agenda1_.category as category2_0_0_,
       agenda1_.contents as contents3_0_0_,
       agenda1_.display_yn as display_4_0_0_,
       agenda1_.nickname as nickname5_0_0_,
       agenda1_.reg_dt as reg_dt6_0_0_,
       agenda1_.subject as subject7_0_0_,
       agenda1_.tag_1 as tag_8_0_0_,
       agenda1_.tag_2 as tag_9_0_0_,
       agenda1_.tag_3 as tag_10_0_0_,
       agenda1_.thumbnail as thumbna11_0_0_,
       agenda1_.upd_dt as upd_dt12_0_0_,
       agenda1_.user_id as user_id13_0_0_,
       agenda1_.versus_1 as versus_14_0_0_,
       agenda1_.versus_2 as versus_15_0_0_
from AGENDA_STATISTICS agendastat0_
      left outer join AGENDA_MASTER
          agenda1_ on agendastat0_.agenda_id = agenda1_.agenda_id
where agendastat0_.agenda_id=85;




http://127.0.0.1:8080/agenda/api/v1/agenda

{
    "subject": "골라라11111119999999",
    "category": "잡담",
    "userId": 1234134,
    "nickname": "aaaaaa",
    "thumbnail": "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4utc/image/ho3SXNRhZnwe_nLj65FMxCOMHT4.jpg",
    "register": "mrbluesky",
    "versus1": "볼빨간 사춘기",
    "versus2": "사촌간 볼빨기",
    "contents": "뭐가 더 나음???",
    "tag1":"캬캬캬"
}

http://127.0.0.1:8080/agenda/api/v1/agenda/85