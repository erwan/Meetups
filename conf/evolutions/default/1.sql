# --- First database schema

# --- !Ups

set ignorecase true;

create table meetup (
  id                        bigint not null,
  name                      varchar(255) not null,
  created                   timestamp,
  happening                 timestamp,
  place                     varchar(255),
  constraint pk_meetup primary key (id))
;

create sequence meetup_seq start with 1000;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists meetup;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists meetup_seq;

