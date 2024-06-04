CREATE TABLE fornecedor(
    codigo SERIAL PRIMARY KEY,
    descricao VARCHAR(45)
);

CREATE TABLE pessoa(
    codigo SERIAL PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    funcao VARCHAR(50)
);

CREATE TABLE produto(
    codigo SERIAL PRIMARY KEY,
    descricao VARCHAR(50),
    valor DECIMAL(9,2) NOT NULL,
    quantidade INT NOT NULL,
    fornecedor_codigo INT NOT NULL,
    FOREIGN KEY (fornecedor_codigo) REFERENCES fornecedor(codigo)
);

CREATE TABLE venda( 
    codigo SERIAL PRIMARY KEY,
    horario TIMESTAMP NOT NULL,
    valor_total DECIMAL(9,2) NOT NULL,
    pessoa_codigo_vendedor INT NOT NULL,
    pessoa_codigo_comprador INT NOT NULL,
    FOREIGN KEY (pessoa_codigo_vendedor) REFERENCES pessoa(codigo),
    FOREIGN KEY (pessoa_codigo_comprador) REFERENCES pessoa(codigo)
);

CREATE TABLE item (
    codigo SERIAL PRIMARY KEY,
    quantidade INT NOT NULL,
    valor_parcial DECIMAL(9,2) NOT NULL,
    produto_codigo INT NOT NULL,
    venda_codigo INT NOT NULL,
    FOREIGN KEY (produto_codigo) REFERENCES produto(codigo),
    FOREIGN KEY (venda_codigo) REFERENCES venda(codigo)
);