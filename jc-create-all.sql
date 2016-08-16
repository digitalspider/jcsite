create table server (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  cdate                         date,
  mdate                         date,
  status                        varchar(255),
  ip                            varchar(255),
  alias                         varchar(255),
  os                            varchar(255),
  architecture                  varchar(255),
  osversion                     varchar(255),
  mem_max                       varchar(255),
  mem_peak                      varchar(255),
  memcurrent                    varchar(255),
  hdd_max                       varchar(255),
  hdd_peak                      varchar(255),
  hdd_current                   varchar(255),
  sshkey                        varchar(255),
  parent_server_id              integer,
  constraint pk_server primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  cdate                         date,
  mdate                         date,
  status                        varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  constraint pk_user primary key (id)
);

