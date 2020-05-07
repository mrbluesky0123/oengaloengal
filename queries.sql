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
                                     `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                     `agendaid` bigint(20) unsigned zerofill NOT NULL COMMENT '논제ID',
                                     `userid` varchar(20) NOT NULL COMMENT '작성자',
                                     `dislikeflag` varchar(1) NOT NULL COMMENT '좋아요여부',
                                     `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


RENAME TABLE `BOARD_MASTER` TO `AGENDA_MASTER`;
