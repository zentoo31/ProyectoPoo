create database UCV
go

use ucv
go

--Creacion de tabla usuarios 
create table UCV..Usuarios(
	ID int identity,
	Nombre varchar(50) not null,
	Apellido varchar(50) not null,
	Usuario varchar(100) not null,
	Contraseña varchar(100) not null,
	FechaRegistro varchar(100) not null
	constraint pk_ID primary key (ID)
);
go

insert into UCV..Usuarios(Nombre,Apellido,Usuario,Contraseña,FechaRegistro)
values('Juan', 'Gonzales', 'juan123', '$2a$12$HuAHfhHnNgQpEdH1wEbScOxiTXPptu5AEffPX4PpD4lazaSGC0HAK', '1-11-2023')
go

insert into UCV..Usuarios(Nombre,Apellido,Usuario,Contraseña,FechaRegistro)
values('Guillermo', 'De Luque', 'guillem12', '$2a$12$HuAHfhHnNgQpEdH1wEbScOxiTXPptu5AEffPX4PpD4lazaSGC0HAK', '30-05-2022')
go

select * from UCV..Usuarios
go


--Creacion de tabla producto 
create table UCV..Producto(
	codigo varchar(50) not null,
	nombre varchar(100) not null,
	cantidad int not null,
	unidad varchar(50) not null,
	vencimiento varchar(50) not null,
	categoria varchar(100) not null
	constraint pk_codigo primary key (codigo)
);
go

insert into UCV..Producto(codigo,nombre,cantidad,unidad,vencimiento,categoria)
values('A001','Analgésico Acetaminofén',100,'Tableta','2024-12-31','Analgesicos')
go
insert into UCV..Producto(codigo,nombre,cantidad,unidad,vencimiento,categoria)
values('A002','Analgésico Ibuprofeno',50,'Tableta','2024-12-31','Analgesicos')
go
insert into UCV..Producto(codigo,nombre,cantidad,unidad,vencimiento,categoria)
values('A003','Antibiótico Amoxicilina',50,'Cápsula','2025-06-30','Antibiotico')
go

select nombre from UCV..Producto
go

--Creacion de tabla de estudiantes

create table UCV..Estudiantes(
    ID int identity,
    Nombres varchar(50) not null,
    Matricula varchar(100) not null,
    FechaRegistro varchar(100) not null,
    constraint pk_ID_estudiante primary key (ID)
);


insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('García López Juan Carlos', '20230001', '01-02-2023');

insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('Rodríguez Sánchez María José', '20230002', '15-03-2023');

insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('Martínez González Carlos Andrés', '20230002', '15-03-2023');

insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('Pérez Ramírez Laura Sofía', '20230002', '15-03-2023');

insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('González Torres José Luis', '20230001', '01-02-2023');

insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('Sánchez Díaz Ana Gabriela', '20230001', '01-02-2023');

insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('Romero Vargas Luis Eduardo', '20220002', '15-03-2023');

insert into UCV..Estudiantes(Nombres, Matricula, FechaRegistro)
values('Díaz Herrera Paola Alejandra', '20220002', '15-03-2023');

select * from Estudiantes
go

--Creacion de tabla de registroDeEntregas

create table UCV..RegistroEntregas(
    ID int identity,
    EstudianteID int not null,
    ProductoCodigo varchar(50) not null,
    CantidadEntregada int not null,
    FechaEntrega varchar(50) not null,
	constraint fk_farmaceuticoID foreign key (ProductoCodigo) references UCV..Producto(codigo),
	constraint fk_EstudianteID foreign key (EstudianteID) references UCV..Estudiantes(ID)
);

insert into UCV..RegistroEntregas(EstudianteID, ProductoCodigo, CantidadEntregada, FechaEntrega)
values(1, 'A001', 2, '02-11-2023');

insert into UCV..RegistroEntregas(EstudianteID, ProductoCodigo, CantidadEntregada, FechaEntrega)
values(2, 'A002', 1, '16-11-2023');

Select * from RegistroEntregas
go

SELECT r.*, e.Nombres AS NombresEstudiante FROM RegistroEntregas r JOIN Estudiantes e ON r.EstudianteID = e.ID
go




