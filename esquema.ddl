create sequence ejercicio_seq start with 1 increment by 50;
create sequence fragmento_rutina_seq start with 1 increment by 50;
create sequence rutina_seq start with 1 increment by 50;
create table ejercicio (id bigint not null, id_entrenador bigint, descripcion varchar(255), dificultad varchar(255), material varchar(255), musculos_trabajados varchar(255), nombre varchar(255), observaciones varchar(255), tipo varchar(255), primary key (id));
create table ejercicio_multimedia (ejercicio_id bigint not null, multimedia varchar(255));
create table fragmento_rutina (duracion_minutos integer, repeticiones integer, series integer, ejercicio_id bigint, id bigint not null, primary key (id));
create table rutina (id bigint not null, id_entrenador bigint, descripcion varchar(255), nombre varchar(255), observaciones varchar(255), primary key (id));
create table rutina_ejercicios (ejercicios_id bigint not null unique, rutina_id bigint not null);
alter table if exists ejercicio_multimedia add constraint FKqfgc5ud05wxurkemm8qnw20n1 foreign key (ejercicio_id) references ejercicio;
alter table if exists fragmento_rutina add constraint FK85d8i9rl0h1b5hpder6lakp2l foreign key (ejercicio_id) references ejercicio;
alter table if exists rutina_ejercicios add constraint FKq6jic6isp5qhj6q6lkus7s5uv foreign key (ejercicios_id) references fragmento_rutina;
alter table if exists rutina_ejercicios add constraint FKmcb9eic73952ml9qd0113uq1n foreign key (rutina_id) references rutina;
