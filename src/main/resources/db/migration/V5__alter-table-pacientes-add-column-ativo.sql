alter table pacientes
    add ativo tinyint;

update pacientes
set pacientes.ativo = 1;