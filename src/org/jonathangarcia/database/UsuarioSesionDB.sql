Use SuperDB;
-- SET GLOBAL time_zone = '-6:00';

-- Nivel de Acceso --
Create table NivelAcceso(
	nivelAccesoId int not null auto_increment,
    nivelAcceso varchar(40) not null,
    primary key PK_nivelAccesoId(nivelAccesoId)
);

Insert into NivelAcceso(nivelAcceso) values
	('Admin'),
    ('Usuario');

-- Usuario --
Create table Usuarios(
	usuarioId int not null auto_increment,
    usuario varchar(30) not null,
    contrasenia varchar(100) not null,
    nivelAccesoId int not null,
    empleadoId int not null,
    primary key PK_usuarioId (usuarioId),
    constraint FK_Usuarios_NivelAcceso foreign key Usuarios(nivelAccesoId)
		references NivelAcceso(nivelAccesoId),
	constraint FK_Usuarios_Empleados foreign key Usuarios(empleadoId)
		references Empleados(empleadoId)
);

-- Sp Agregar Usuario -- 

Delimiter $$
Create procedure sp_agregarUsuario(us varchar(40), con varchar(100), nivAccId int, empleId int)
Begin
	insert into Usuarios(usuario, contrasenia, nivelAccesoId, empleadoId) values
		(us, con, 1, empleId);
End $$
Delimiter ;

call sp_agregarUsuario('Jgarcia', 'Wux676147', 1, 2);

Select * from Empleados;

call sp_AsignarEncargado(1,2);

Select * from Usuarios;

-- SP Buscar Usuario --
Delimiter $$
create procedure sp_buscarUsario(us varchar(30))
Begin
	Select * from Usuarios
		Where usuario = us;
End $$
Delimiter ;



