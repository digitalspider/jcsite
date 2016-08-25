use jcloud;

drop table if exists server;
drop table if exists user;
drop table if exists role;
drop table if exists userrole;

create table server (
  id                            bigint auto_increment not null,
  name                          varchar(63),
  cdate                         date,
  mdate                         date,
  status                        varchar(16),
  ip                            varchar(16),
  alias                         varchar(16),
  os                            varchar(16),
  architecture                  varchar(16),
  osversion                     varchar(16),
  mem_max                       int,
  mem_peak                      int,
  memcurrent                    int,
  hdd_max                       int,
  hdd_peak                      int,
  hdd_current                   int,
  sshkey                        varchar(255),
  parent_server_id              integer,
  constraint pk_server primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(63),
  cdate                         date,
  mdate                         date,
  status                        varchar(16),
  email                         varchar(63),
  password                      varchar(63),
  first_name                    varchar(63),
  last_name                     varchar(63),
  constraint pk_user primary key (id)
);

create table role (
  role                          varchar(16),
  constraint pk_role primary key (role)
);

create table userrole (
  name                          varchar(63),
  role                          varchar(16),
  constraint pk_userrole primary key (name,role)
);

insert into role (role) VALUES ('admin');
insert into role (role) VALUES ('manager');
insert into role (role) VALUES ('user');

