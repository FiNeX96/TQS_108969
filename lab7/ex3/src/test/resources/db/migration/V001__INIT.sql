CREATE TABLE student(
  nome VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  nmec INT PRIMARY KEY NOT NULL,
  email VARCHAR(255) NOT NULL
);

INSERT INTO student (nome, age, nmec, email) VALUES ('João', 20, 999, 'joao@ua.pt');
INSERT INTO student (nome, age, nmec, email) VALUES ('Maria', 21, 888, 'maria@ua.pt');
