CREATE TABLE CUIDADO (
	CALIDAD VARCHAR NOT NULL,
	NIVEL INT4 NOT NULL,
	EVALUACION CHAR(3) NOT NULL,
	ID_CUIDADO INTEGER,
	id_intensivista INTEGER,
	dni INTEGER,
	id_inspector INTEGER,
	PRIMARY KEY (ID_CUIDADO)
);

CREATE TABLE Personal (
	Solapin integer Unique, 
	Nombre varchar NOT NULL, 
	Sexo varchar NOT NULL, 
	PRIMARY KEY (Solapin)
);

CREATE TABLE Inspector ( 
	Años_Servicio int4 NOT NULL, 
	id_inspector integer Unique, 
	PRIMARY KEY (Solapin, id_inspector)
)INHERITS(Personal);

CREATE TABLE Intensivista (
	Nivel integer NOT NULL,
	id_intensivista integer Unique,
	PRIMARY KEY (Solapin)
)INHERITS(Personal);

CREATE TABLE Sala (
	Piso integer NOT NULL, 
	Num_sala integer NOT NULL, 
	cant_medicos int4 NOT NULL, 
	Cant_instrumentos int4 NOT NULL,  
	PRIMARY KEY (Num_sala)
);

CREATE TABLE Intensivista_telefono (
	num_tel varchar NOT NULL, 
	Solapin int4 NOT NULL, 
	PRIMARY KEY (num_tel, Solapin)
);

CREATE TABLE Paciente_Sala_Intensivista (
	dni int4 NOT NULL, 
	Num_sala integer NOT NULL, 
	Solapin int4 NOT NULL,
	id_parte int4 NOT NULL,
	ident integer,
	PRIMARY KEY(ident)
);

CREATE TABLE Parte_medico (
	id_parte SERIAL NOT NULL, 
	fecha time(6) NOT NULL,
	Estado_paciente varchar(255) NOT NULL, 
	PRIMARY KEY (id_parte)
);

CREATE TABLE Paciente (
	dni integer NOT NULL,  
	Sexo varchar NOT NULL, 
	PRIMARY KEY (dni)
);

CREATE TABLE Parte_medico_Paciente(
	id_parte SERIAL NOT NULL,
	dni integer NOT NULL,
	cama varchar(255) NOT NULL
);

CREATE TABLE Municipio(
	id_municipio integer,
	dni integer NOT NULL,
	nombre varchar NOT NULL,
	PRIMARY KEY (id_municipio)
);

CREATE TABLE Provincia(
	id_municipio integer,
	nombre varchar NOT NULL,
	id_provincia integer,
	PRIMARY KEY (id_provincia)
);

CREATE TABLE Pais(
	id_provincia integer NOT NULL,
	nombre varchar NOT NULL,
	id_pais integer,
	PRIMARY KEY (id_pais)
);

CREATE TABLE Medicamento (
	id_Medicamento SERIAL NOT NULL, 
	tipo_medicamento varchar(255) NOT NULL, 
	nombre varchar(255) NOT NULL,  
	PRIMARY KEY (id_Medicamento)
);

CREATE TABLE Paciente_Medicamento (
	id_Medicamento SERIAL NOT NULL, 
	dni integer NOT NULL
);
	
CREATE TABLE Registro (
	id_registro int4 NOT NULL, 
	Frecuencia int4 NOT NULL, 
	Dosis varchar(255) NOT NULL,   
	fecha time(6) NOT NULL, 
	PRIMARY KEY (id_registro)
);

CREATE TABLE Registro_medicamento(
	id_Medicamento int4 NOT NULL,
	id_registro int4 NOT NULL
);


/*-----------------------------------------------------*/

ALTER TABLE Cuidado 
	ADD CONSTRAINT FKCuidadoNumReg 
	FOREIGN KEY (id_inspector) 
	REFERENCES Inspector (id_inspector);

ALTER TABLE Cuidado 
	ADD CONSTRAINT FKCuidadoPaciente
	FOREIGN KEY (dni) 
	REFERENCES Paciente (dni);

ALTER TABLE Cuidado 
	ADD CONSTRAINT FKCuidadoIntensivista
	FOREIGN KEY (id_intensivista) 
	REFERENCES Intensivista (id_intensivista);

ALTER TABLE Intensivista_telefono 
	ADD CONSTRAINT FKIntensivis493683 
	FOREIGN KEY (Solapin) 
	REFERENCES Intensivista (Solapin);

ALTER TABLE Paciente_Sala_Intensivista 
	ADD CONSTRAINT FKPaciente_S699993 
	FOREIGN KEY (dni) 
	REFERENCES Paciente (dni);

ALTER TABLE Paciente_Sala_Intensivista 
	ADD CONSTRAINT FKPaciente_S480891 
	FOREIGN KEY (Num_sala) 
	REFERENCES Sala (Num_sala);

ALTER TABLE Paciente_Sala_Intensivista 
	ADD CONSTRAINT FKPaciente_S45009
	FOREIGN KEY (Solapin) 
	REFERENCES Intensivista (Solapin);	

ALTER TABLE Parte_medico_Paciente 
	ADD CONSTRAINT FKPacienteParteMedico
	FOREIGN KEY (dni) 
	REFERENCES Paciente (dni);	

ALTER TABLE Parte_medico_Paciente 
	ADD CONSTRAINT FKPacienteParteMedicoB
	FOREIGN KEY (id_parte) 
	REFERENCES Parte_medico (id_parte);	

ALTER TABLE Municipio 
	ADD CONSTRAINT FKMunicipio
	FOREIGN KEY (dni) 
	REFERENCES Paciente (dni);

ALTER TABLE Provincia 
	ADD CONSTRAINT FKProvincia
	FOREIGN KEY (id_municipio) 
	REFERENCES Municipio (id_municipio);	

ALTER TABLE Pais 
	ADD CONSTRAINT FKPais
	FOREIGN KEY (id_provincia) 
	REFERENCES Provincia (id_provincia);	

ALTER TABLE Paciente_Medicamento 
	ADD CONSTRAINT FKPaciente_Medicamento
	FOREIGN KEY (dni) 
	REFERENCES Paciente (dni);	

ALTER TABLE Paciente_Medicamento
	ADD CONSTRAINT FKPaciente_MedicamentoB
	FOREIGN KEY (id_Medicamento) 
	REFERENCES Medicamento (id_Medicamento);	

ALTER TABLE Registro_medicamento
	ADD CONSTRAINT FKRegistro_medicamento
	FOREIGN KEY (id_Medicamento) 
	REFERENCES Medicamento (id_Medicamento);	

ALTER TABLE Registro_medicamento
	ADD CONSTRAINT FKRegistro_medicamentoB
	FOREIGN KEY (id_registro) 
	REFERENCES Registro (id_registro);
