alter table server drop constraint if exists fk_server_os_id;
drop index if exists ix_server_os_id;

alter table server drop constraint if exists fk_server_user_id;
drop index if exists ix_server_user_id;

alter table userrole drop constraint if exists fk_userrole_user;
drop index if exists ix_userrole_user;

alter table userrole drop constraint if exists fk_userrole_role;
drop index if exists ix_userrole_role;

drop table if exists os;

drop table if exists role;

drop table if exists server;

drop table if exists user;

drop table if exists userrole;

