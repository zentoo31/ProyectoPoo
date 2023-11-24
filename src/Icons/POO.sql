create database UCV
go
--Creacion de tabla usuarios 
create table UCV..Usuarios(
	ID int identity,
	Nombre varchar(50) not null,
	Apellido varchar(50) not null,
	Usuario varchar(100) not null,
	Contrase�a varchar(100) not null,
	FechaRegistro varchar(100) not null

	constraint pk_ID primary key (ID)
);
go

insert into UCV..Usuarios(Nombre,Apellido,Usuario,Contrase�a,FechaRegistro)
values('Juan', 'Gonzales', 'juan123', '$2a$12$HuAHfhHnNgQpEdH1wEbScOxiTXPptu5AEffPX4PpD4lazaSGC0HAK', '1-11-2023')
go

insert into UCV..Usuarios(Nombre,Apellido,Usuario,Contrase�a,FechaRegistro)
values('Guillermo', 'De Luque', 'guillem12', '$2a$12$HuAHfhHnNgQpEdH1wEbScOxiTXPptu5AEffPX4PpD4lazaSGC0HAK', '30-05-2022')
go

select * from UCV..Usuarios
go

select * from UCV..Usuarios
where Usuario = 'juan123'
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
values('A001','Analg�sico Acetaminof�n',100,'Tableta','2024-12-31','Analgesicos')
go
insert into UCV..Producto(codigo,nombre,cantidad,unidad,vencimiento,categoria)
values('A002','Analg�sico Ibuprofeno',50,'Tableta','2024-12-31','Analgesicos')
go
insert into UCV..Producto(codigo,nombre,cantidad,unidad,vencimiento,categoria)
values('A003','Antibi�tico Amoxicilina',50,'C�psula','2025-06-30','Antibiotico')
go

select * from UCV..Producto
go