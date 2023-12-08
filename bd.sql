CREATE TABLE pecas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);

CREATE TABLE fornecedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(255) NOT NULL,
    cnpj CHAR(14) NOT NULL
);

CREATE TABLE pecas_fornecedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    peca_id INT NOT NULL,
    fornecedor_id INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (peca_id) REFERENCES pecas(id) ON DELETE CASCADE,
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id) ON DELETE CASCADE
);
