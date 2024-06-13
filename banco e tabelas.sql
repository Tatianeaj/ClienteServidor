create database recrutamento;
use recrutamento;

create table Candidato(
id_candidato int auto_increment,
nome char(50) not null,
email char(50) not null,
senha char(50) not null,
primary key(id_candidato)
);
#insert into Candidato(nome, email, senha) values ("sabrina", "sa@sa.com","1234567");

create table Competencia(
id_competencia int auto_increment,
competencia char(50) not null,
primary key(id_competencia));

create table Empresa(
id_empresa int auto_increment,
razao_social char(50) not null,
cnpj char(50) not null,
email char(50) not null,
senha char(50) not null,
ramo char(50) not null,
descricao char(255) not null,
primary key(id_empresa)
);

create table Candidato_Competencia(
id_candidato_competencia int auto_increment,
tempo int not null,
id_candidato int not null,
id_competencia int not null,
primary key(id_candidato_competencia),
foreign key(id_candidato) references Candidato(id_candidato),
foreign key(id_competencia) references Competencia(id_competencia)
);

create table Vaga(
id_vaga int auto_increment,
faixa_salarial double not null,
descricao char(255) not null,
id_empresa int not null,
primary key(id_vaga),
foreign key(id_empresa) references Empresa(id_empresa)
);

create table Candidato_Vaga(
id_candidato_vaga int auto_increment,
visualizou bool not null,
id_candidato int not null,
id_vaga int not null,
primary key(id_candidato_vaga),
foreign key(id_candidato) references Candidato(id_candidato),
foreign key(id_vaga) references Vaga(id_vaga)
);

create table Vaga_Competencia (
id_vaga_competencia int auto_increment,
tempo int not null,
id_competencia int not null,
id_vaga int not null,
primary key(id_vaga_competencia),
foreign key(id_competencia) references Competencia(id_competencia),
foreign key(id_vaga) references Vaga(id_vaga)
);