create table os (
  id                            bigint auto_increment not null,
  status                        integer,
  distribution                  varchar(255),
  version                       varchar(255),
  architecture                  varchar(255),
  cdate                         timestamp not null,
  mdate                         timestamp not null,
  constraint pk_os primary key (id)
);

create table role (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_role primary key (id)
);

create table server (
  id                            bigint auto_increment not null,
  status                        integer,
  name                          varchar(255),
  lxd_id                        varchar(255),
  ip                            varchar(255),
  alias                         varchar(255),
  os_id                         bigint not null,
  architecture                  varchar(255),
  os_version                    varchar(255),
  description                   varchar(255),
  pdate                         timestamp,
  price                         double,
  cpu_limit                     double,
  cpu_current                   double,
  cpu_peak                      double,
  mem_limit                     double,
  mem_current                   double,
  mem_peak                      double,
  hdd_limit                     double,
  hdd_current                   double,
  hdd_peak                      double,
  ssh_key                       varchar(255),
  parent_server_id              bigint,
  user_id                       bigint not null,
  cdate                         timestamp not null,
  mdate                         timestamp not null,
  constraint pk_server primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  status                        integer,
  username                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  cdate                         timestamp not null,
  mdate                         timestamp not null,
  constraint pk_user primary key (id)
);

create table userrole (
  user_id                       bigint not null,
  role_id                       bigint not null,
  constraint pk_userrole primary key (user_id,role_id)
);

alter table server add constraint fk_server_os_id foreign key (os_id) references os (id) on delete restrict on update restrict;
create index ix_server_os_id on server (os_id);

alter table server add constraint fk_server_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_server_user_id on server (user_id);

alter table userrole add constraint fk_userrole_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_userrole_user on userrole (user_id);

alter table userrole add constraint fk_userrole_role foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_userrole_role on userrole (role_id);

