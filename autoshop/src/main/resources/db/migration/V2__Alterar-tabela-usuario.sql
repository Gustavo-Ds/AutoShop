ALTER table usuarios
add email varchar2(150);

alter table usuarios
add constraint uq_usuarios_email UNIQUE (email);

alter table usuarios
modify (email not null);
