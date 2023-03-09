alter table medicos
    add ativo tinyint;

update medicos
set medicos.ativo = 1;