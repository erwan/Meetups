# --- Sample dataset

# --- !Ups

insert into meetup (id,name,created,happening,place) values (1, 'ParisJS', '2012-01-15 00:00:00', '2012-01-25 19:30:00', 'Paris 17');
insert into meetup (id,name,created,happening,place) values (2, 'ZRM', '2012-01-15 00:00:00', '2012-02-01 12:30:00', 'Taitbout');
insert into meetup (id,name,created,happening,place) values (3, 'Devoxx France', '2012-01-15 00:00:00', '2012-04-18 09:30:00', 'Paris');

# --- !Downs

delete from meetup;
