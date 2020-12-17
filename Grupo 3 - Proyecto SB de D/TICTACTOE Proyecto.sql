CREATE DATABASE IF NOT EXISTS TicTacToe;
use TicTacToe;

CREATE TABLE IF NOT EXISTS Jugador(
	usuario varchar(20),
    nombre varchar(25) not null,
    apellido varchar(35) not null,
    contraseña varchar(16) not null,
    estado smallint not null,
    correo varchar(40) not null,
    sexo enum('MASCULINO','FEMENINO') not null,
    fecha_Nacimiento date,
    CONSTRAINT pk_Jugador PRIMARY KEY (usuario),
    CONSTRAINT un_password UNIQUE (contraseña,correo)
);

CREATE TABLE IF NOT EXISTS Partida(
	id int,
    fechaInicio date,
    horaInicio time,
    fechaFin date,
    horaFin time,
    ganador varchar(20),
    estado smallint,
    CONSTRAINT pk_Partida PRIMARY KEY (id),
    CONSTRAINT fk_Partida FOREIGN KEY (ganador) REFERENCES Jugador (usuario)
);
-- INSERT INTO Partida (id, fechaInicio, horaInicio) VALUES (1,'2020-12-13','13:05:06');
-- UPDATE Partida SET fechaFin = CURDATE(), horaFin = '13:15:57', ganador = 'AJzro3', estado = 1 WHERE id = 1;
-- UPDATE Partida SET horaFin = '3:15:57' WHERE id = 1;
CREATE TABLE IF NOT EXISTS Campeonato(
	id int,
    fecha_Campeonato date,
	CONSTRAINT pk_Campeonato PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Asigna(
	partida int,
    campeonato int,
    ganador varchar(20),
    clasificacion int,
    CONSTRAINT un_Asigna_partida UNIQUE (partida),
    CONSTRAINT pk_Asigna PRIMARY KEY (partida,campeonato),
    CONSTRAINT fk_Asigna_partida FOREIGN KEY (partida) REFERENCES Partida (id),
    CONSTRAINT fk_Asigna_campeonato FOREIGN KEY (campeonato) REFERENCES Campeonato (id),
    CONSTRAINT fk_Asigna_ganador FOREIGN KEY (ganador) REFERENCES Jugador (usuario)
);

CREATE TABLE IF NOT EXISTS Juega(
	partida int,
    jugador varchar(20),
    CONSTRAINT pk_Juega PRIMARY KEY (partida, jugador),
    CONSTRAINT fk_Juega_partida FOREIGN KEY (partida) REFERENCES Partida (id),
    CONSTRAINT fk_Juega_jugador FOREIGN KEY (jugador) REFERENCES Jugador (usuario)
);

CREATE TABLE IF NOT EXISTS Ubica(
	idPartida int,
    jugador varchar(20),
    columna smallint,
    fila smallint,
    CONSTRAINT pk_Ubica PRIMARY KEY (idPartida, jugador, columna, fila),
    CONSTRAINT fk_Ubica_idPartida FOREIGN KEY (idPartida) REFERENCES Partida (id),
    CONSTRAINT fk_Ubica_jugador FOREIGN KEY (jugador) REFERENCES Jugador (usuario)
);
-- DELETE FROM Partida WHERE id = 1;

-- DELETE FROM Jugador WHERE usuario = 'AJzro3';
-- INSERT INTO Juega values(1,'AJzro3');
-- DELETE FROM Juega WHERE partida = 1;
-- INSERT INTO Jugador values('AJzro3','Andres','Medina','dz03',1,'thunderandrs67@hotmail.com','MASCULINO','2001-2-10'),('AJ','Andres','Medina','lol',1,'andres7@hotmail.com','MASCULINO','2001-2-10');
-- UPDATE Partida SET fechaFin = CURDATE(), horaFin = CURTIME(), estado = 2 WHERE id = 5;
