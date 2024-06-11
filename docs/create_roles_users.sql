-- CREATE GROUP ROLES

create role vendedor password 'VendedorFoda12$';

grant select on pessoa to vendedor;
grant select on produto to vendedor;
grant select, insert, update on item to vendedor;
grant select, insert, update on venda to vendedor;

create role gerente password 'GerenteLouco84%';

grant all on pessoa to gerente;
grant all on produto to gerente;
grant all on item to gerente;
grant all on venda to gerente;
grant all on fornecedor to gerente;


-- CREATE USERS

create user juliano password 'jujuFoda43@#';
grant vendedor to juliano;


