# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  email                     varchar(255) not null,
  password                  varchar(255),
  role                      integer,
  active                    tinyint(1) default 0,
  constraint pk_account primary key (email))
;

create table answer (
  id                        bigint auto_increment not null,
  answer_value              varchar(255),
  question_id               bigint,
  candidate_id              bigint,
  constraint pk_answer primary key (id))
;

create table candidate (
  id                        bigint auto_increment not null,
  full_name                 varchar(255),
  birthday                  date,
  gender                    varchar(255),
  address                   varchar(255),
  phone_number              varchar(255),
  experience                integer,
  language                  varchar(255),
  position                  varchar(255) not null,
  level                     varchar(255),
  exam_id                   bigint,
  account_email             varchar(255),
  constraint pk_candidate primary key (id))
;

create table exam (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  duration                  integer not null,
  number_of_question        integer not null,
  language                  varchar(255) not null,
  constraint uq_exam_name unique (name),
  constraint pk_exam primary key (id))
;

create table exam_question (
  id                        bigint auto_increment not null,
  question_id               bigint,
  exam_id                   bigint,
  sequence                  integer not null,
  constraint pk_exam_question primary key (id))
;

create table question (
  id                        bigint auto_increment not null,
  language                  varchar(255) not null,
  type                      integer not null,
  level                     varchar(255) not null,
  content                   TEXT not null,
  answer_value              varchar(255) not null,
  constraint pk_question primary key (id))
;

create table quiz (
  id                        bigint auto_increment not null,
  content                   TEXT,
  sequence                  integer,
  question_id               bigint,
  constraint pk_quiz primary key (id))
;

create table score (
  user_email                varchar(255) not null,
  user_score                integer,
  constraint pk_score primary key (user_email))
;

alter table answer add constraint fk_answer_question_1 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_question_1 on answer (question_id);
alter table answer add constraint fk_answer_candidate_2 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;
create index ix_answer_candidate_2 on answer (candidate_id);
alter table candidate add constraint fk_candidate_exam_3 foreign key (exam_id) references exam (id) on delete restrict on update restrict;
create index ix_candidate_exam_3 on candidate (exam_id);
alter table candidate add constraint fk_candidate_account_4 foreign key (account_email) references account (email) on delete restrict on update restrict;
create index ix_candidate_account_4 on candidate (account_email);
alter table exam_question add constraint fk_exam_question_question_5 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_exam_question_question_5 on exam_question (question_id);
alter table exam_question add constraint fk_exam_question_exam_6 foreign key (exam_id) references exam (id) on delete restrict on update restrict;
create index ix_exam_question_exam_6 on exam_question (exam_id);
alter table quiz add constraint fk_quiz_question_7 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_quiz_question_7 on quiz (question_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table answer;

drop table candidate;

drop table exam;

drop table exam_question;

drop table question;

drop table quiz;

drop table score;

SET FOREIGN_KEY_CHECKS=1;

