Use SuperDB;
SET GLOBAL time_zone = '-6:00';

-- ======================== Crud de Cargo ======================== --

Delimiter $$
Create Procedure sp_agregarCargo(in nomCarg varchar(30),in descripCarg varchar(100))
	Begin
		insert into Cargos(nombreCargo, descripcionCargo) values
			(nomCarg, descripCarg);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarCargo()
	Begin
		Select
			Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
				From Cargos;
	End$$
Delimiter;

Delimiter $$
create procedure sp_eliminarCargo(in cargId int)
	Begin 
		Delete from Cargos
			Where cargoId = cargId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarCargo(in cargId int)
	Begin
		Select
            Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
            
				From Cargos
					Where cargoId = cargId;
	End$$
Delimiter ;
             
Delimiter $$
Create Procedure sp_editarCargo(in cargId int,in nomCar varchar(30), in descripCarg varchar(100))
	Begin
		Update Cargos
			Set
				nombreCargo = nomCar,
				descripcionCargo = descripCarg
					Where cargoId = cargId;
	End$$
Delimiter ;

-- ======================== Crud de CategoriaProductos ======================== --

Delimiter $$
Create Procedure sp_agregarCategoriaProducto(in nomCateg varchar(30),in descripCateg varchar(100))
	Begin
		insert into CategoriaProductos(nombreCategoria, descripcionCategoria) values
			(nomCateg, descripCateg);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarCategoriaProductos()
	Begin
		Select
			CategoriaProductos.categoriaProductosId,
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
				From CategoriaProductos;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarCategoriaProductos(in categoProdId int)
	Begin 
		Delete from CategoriaProductos
			Where categoriaProductosId = categoProdId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarCategoriaProductos(in categoProdId int)
	Begin
		select
            CategoriaProductos.categoriaProductosId,
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
				From CategoriaProductos
					Where categoriaProductosId = categoProdId;
	End$$
Delimiter ;
             
Delimiter $$
Create Procedure sp_editarCategoriaProductos(in categoProdId int,in nomCateg varchar(30), in descripCateg varchar(100))
	Begin
		Update CategoriaProductos
			Set
				nombreCategoria = nomCateg,
				descripcionCategoria = descripCateg
					Where categoriaProductosId = categoProdId;
	End$$
Delimiter ;

-- ======================== Crud de Distribuidores ======================== --

Delimiter $$
Create Procedure sp_agregarDistribuidores(in nomDist varchar(30),in direDistri varchar(200), ntDistri varchar(15), teleDistri varchar(15), wb varchar(50))
	Begin
		insert into Distribuidores(nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web) values
			(nomDist, direDistri, ntDistri, teleDistri, wb); 
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarDistribuidores()
	Begin
		Select
			Distribuidores.distribuidorId,
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
				From Distribuidores;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarDistribuidores(in distriId int)
	Begin 
		Delete from Distribuidores
			Where distribuidorId = distriId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarDistribuidores(in distriId int)
	Begin
		Select
			Distribuidores.distribuidorId,
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
				From Distribuidores
					Where distribuidorId = distriId;
	End$$
Delimiter ;
             
Delimiter $$
Create Procedure sp_editarDistribuidores(in distriId int,in nomDist varchar(30),in direDistri varchar(200), ntDistri varchar(15), teleDistri varchar(15), wb varchar(50))
	Begin
		Update Distribuidores
			Set
				nombreDistribuidor = nomDist,
				direccionDistribuidor = direDistri,
                nitDistribuidor = ntDistri,
                telefonoDistribuidor = teleDistri,
                web = wb
					Where distribuidorId = distriId;
	End$$
Delimiter ;

-- ======================== Crud de Compras ======================== --

Delimiter $$
Create Procedure sp_agregarCompra(in fechCom DATE,in totalComp DECIMAL(10,2))
	Begin
		insert into Compras(fechaCompra, totalCompra) values
			(fechCom, totalComp);
	End$$
Delimiter ;

-- Listar
Delimiter $$
Create Procedure sp_listarCompra()
	Begin
		select
			Compras.compraId,
            Compras.fechaCompra,
            Compras.totalCompra
				from Compras;
	End$$
Delimiter ;
call sp_listarCompra;
-- Eliminar
Delimiter $$
Create Procedure sp_eliminarCompra(in compId int)
	Begin 
		delete from Compras
			where compraId = compId;
	End$$
Delimiter ;

-- Buscar
Delimiter $$
Create Procedure sp_buscarCompra(in compId int)
	Begin
		select
			Compras.compraId,
            Compras.fechaCompra,
            Compras.totalCompra
				from Compras
					where compraId = compId;
	End$$
delimiter ;
             
-- Editar
Delimiter $$
Create Procedure sp_editarCompra(in compId int,in fecCom date, in totalCom decimal(10,2))
	Begin
		Update Compras
			Set
				fechaCompra = fechCom,
				totalCompra = totalComp
					Where compraId = compId;
	End$$
Delimiter ;

-- ======================== Crud de Clientes ======================== --

Delimiter $$
Create procedure sp_agregarClientes(nom varchar(30), ape varchar(30), tel varchar(15), dir varchar(200), nt varchar(11))
begin
				insert into Clientes(nombre, apellido, telefono, direccion, nit)
						values (nom, ape, tel, dir, nt);
end$$
Delimiter ;

delimiter $$
create procedure sp_listaClientes()
	begin
		select 
			Clientes.clienteId,
			Clientes.nombre,
			Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion,
            Clientes.nit
				from Clientes;
	end$$
delimiter ;

delimiter $$
create procedure sp_eliminarClientes(in clidId  int)
	begin
		delete from Clientes
			where clienteId  = clidId;
	end$$
delimiter ;

delimiter $$
create procedure sp_buscarClientes(in clidId int)
	begin
		select
			Clientes.clienteId,
			Clientes.nombre,
			Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion,
            Clientes.nit
            from Clientes
					where clienteId = clidId;
	end$$
delimiter ;

delimiter $$
create procedure sp_editarClientes(in clidId int, in nom varchar(30), in ape varchar(30), in tel varchar(15), in dir varchar(200), in nt varchar(11))
	begin	
		update Clientes
			set
				nombre = nom,
                apellido = ape,
                telefono = tel,
                direccion = dir,
                nit = nt
					where clienteId = clidId;
	end$$
delimiter ;

call sp_ListaClientes();

-- ======================== Crud de Productos ======================== --

Delimiter $$
Create Procedure sp_agregarProducto(in nomProd varchar(50),in descProd varchar(100), in cantSto int, in preVenUnit decimal(10, 2), in preVenMay decimal(10,2), in precioCompra decimal(10, 2), in imagProd blob, in distriId int, in categoProdId int)
	Begin
		insert into Productos(nombreProducto,descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor,precioCompra, imagenProducto, distribuidorId, categoriaProductosId) values
			(nomProd, descProd, cantSto, preVenUnit, preVenMay, precioCompra, imagProd, distriId, categoProdId);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarProducto()
	Begin
		Select
			Productos.productoId,
            Productos.nombreProducto,
            Productos.descripcionProducto,
            Productos.cantidadStock,
            Productos.precioVentaUnitario,
            Productos.precioVentaMayor,
            Productos.precioCompra,
            Productos.imagenProducto,
            Productos.distribuidorId,
            Productos.categoriaProductosId
				From Productos;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarProducto(in prodcId int)
	Begin 
		Delete from Productos
			Where productoId = prodcId;
	end$$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarProducto(in prodcId int)
	Begin
		Select
			Productos.productoId,
            Productos.nombreProducto,
            Productos.descripcionProducto,
            Productos.cantidadStock,
            Productos.precioVentaUnitario,
            Productos.precioVentaMayor,
            Productos.precioCompra,
            Productos.imagenProducto,
            Productos.distribuidorId,
            Productos.categoriaProductosId
				From Productos
					Where productoId = prodcId;
	End$$
Delimiter ;
             
Delimiter $$
Create Procedure sp_editarProducto(in prodcId int,in nomProd varchar(50),in descProd varchar(100), in cantSto INT, in preVenUnit decimal(10,2), in preVenMay decimal(10,2), precioCompra decimal(10,2), imagProd blob, in distriId int, in categoProdId int)
	Begin
		Update Productos
			Set
				nombreProducto = nomProd,
				descripcionProducto = descProd,
                cantidadStock = cantSto,
                precioVentaUnitario = preVenUnit,
                precioVentaMayor =preVenMay,
                precioCompra = precioCompra,
                Productos = imagenProducto,
                Productos = distribuidorId,
				Productos = categoriaProductosId
					Where productoId = prodcId;
	End$$
Delimiter ;

-- ======================== Crud de Promociones ======================== --

Delimiter $$
Create Procedure sp_agregarPromocion(in precPromo decimal(10,2),in descPromo varchar(100), in fechIn date, in fechFin date, in prodcId int)
	Begin
		insert into Promociones(precioPromocion, descripcionPromocion, fechaInicio, fechaFinal, productoId) values
			(precPromo, descPromo, fechIn, fechFin, prodcId);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarPromocion()
	Begin
		Select
			Promociones.promocionId,
            Promociones.precioPromocion,
            Promociones.descripcionPromocion,
            Promociones.fechaInicio,
            Promociones.fechaFinal,
            Promociones.productoId
				From Promociones;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarPromocion(in promoId int)
	Begin 
		Delete from Promociones
			Where promocionId = promoId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarPromocion(in promoId int)
	Begin
		select
			Promociones.promocionId,
            Promociones.precioPromocion,
            Promociones.descripcionPromocion,
            Promociones.fechaInicio,
            Promociones.fechaFinal,
            Promociones.productoId
				from Promociones
					where promocionId = promoId;
	End$$
Delimiter ;
             
Delimiter $$
Create Procedure sp_editarPromocion(in promoId int,in preProm decimal(10,2),in desProm varchar(100), in fecIn date, in fecFin date, in prodcId int)
	Begin
		update Promociones
			Set
				precioPromocio = precPromo,
				descripcionPromocion = descPromo,
                fechaInicio = fechIn,
                fechaFinal = fechFin,
                productoId = prodcId
					Where promocionId = promoId;
	End$$
Delimiter ;

-- ======================== Crud de DetalleCompras ======================== --

Delimiter $$
Create Procedure sp_agregarDetalleCompra(in cantiCom int,in prodcId int, in compId int)
	Begin
		insert into DetalleCompra(cantidadCompra, productoId, compraId) values
			(cantiCom, prodcId, compId);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarDetalleCompra()
	Begin
		Select
			DetalleCompra.detalleCompraId,
            DetalleCompra.cantidadCompra,
            DetalleCompra.productoId,
            DetalleCompra.compraId
				From DetalleCompra;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarDetalleCompra(in detaCompId int)
	Begin 
		Delete from DetalleCompra
			Where detalleCompraId = detaCompId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarDetalleCompra(in detaCompId int)
	Begin
		Select
			DetalleCompra.detalleCompraId,
            DetalleCompra.cantidadCompra,
            DetalleCompra.productoId,
            DetalleCompra.compraId
				From DetalleCompra
					Where detalleCompraId = detaCompId;
	End$$
delimiter ;
             
Delimiter $$
Create Procedure sp_editarDetalleCompra(in detaCompId int,in cantiCom int,in prodcId int, in compId int)
	Begin
		Update DetalleCompra
			Set
                cantidadCompra = cantiCom,
                productoId = prodcId,
                compraId = compId
					Where detalleCompraId = detaCompId;
	End$$
Delimiter ;

-- ======================== Crud de Empleados ======================== --

Delimiter $$
Create Procedure sp_agregarEmpleados(in nomEmp varchar(30),in apeEmp varchar(30), sue decimal(10, 2), horEntr time, horSal time, in cargId int)
	Begin
		insert into Distribuidores(nombreEmpleado, apellidoEmpleado, sueldo ,horaEntrada, horaSalida, cargoId) values
			(nomEmp, apeEmp, sue, horEntr, horSal, cargId);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarEmpleados()
	Begin
		Select Empleados.empleadoId, Empleados.nombreEmpleado, Empleados.apellidoEmpleado, Empleados.sueldo, Empleados.horaentrada, Empleados.horaSalida,
        Concat('ID: ', Cargos.cargoId , ' | ' , 'Cargo: ', Cargos.nombreCargo) AS 'cargo',
        Concat('ID: ', E2.encargadoId, ' | ' , 'Nombre: ' ,E2.nombreEmpleado) AS 'Encargado' from Empleados 
		JOIN Cargos ON Empleados.cargoId = Cargos.cargoId
		Left JOIN Empleados E2 ON Empleados.encargadoId = E2.empleadoId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarEmpleados(in empleId int)
	Begin 
		Delete from Empleados
			Where empleadoId = empleId;
	End$$
Delimiter ;

Delimiter $$
Create procedure sp_buscarEmpleados(in empleId int)
	Begin
		SELECT Empleados.empleadoId, Empleados.nombreEmpleado, Empleados.apellidoEmpleado, Empleados.sueldo, Empleados.horaentrada, Empleados.horaSalida,
        Concat("Id: ", Cargos.cargoId, " | ", Cargos.nombreCargo) AS 'cargo', 
        Concat(E2.nombreEmpleado, ' ', E2.apellidoEmpleado) AS 'Encargado' from Empleados 
		JOIN Cargos ON Empleados.cargoId = Cargos.cargoId
		Left JOIN Empleados E2 ON E.encargadoId = E2.empleadoId
		WHERE E.empleadoId = empleId;
	End$$
Delimiter ;
             
Delimiter $$
Create Procedure sp_editarEmpleados(in empleId int, in nomEmp varchar(30), in apeEmp varchar(30), in sue decimal(10,2), in horEntr time, in horSal time, in cargId int)
	Begin
		Update Empleados
			Set
				nombreEmpleado = nomEmp,
				apellidoEmpleado = apeEmp,
                sueldo = sue,
                horaEntrada = horEntr,
                horaSalida = horSal,
                Empleados = cargoId
					Where empleadoId = empleId;
	End$$
Delimiter ;

-- ======================== Crud de Factura ======================== --

Delimiter $$
Create Procedure sp_agregarFactura(in fec date, in hr time, in cliId int, in empleId int, in tot int) 
	Begin
		insert into Facturas(fecha, hora, clienteId, empleadoId, total)
			values(fec, hor, clidId, empId, tot);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarFactura()
	Begin
		select
			Facturas.facturaId,
            Facturas.fecha,
            Facturas.hora,
            Facturas.clienteId,
            Facturas.empleadoId,
            Facturas.total
				From Facturas;
	End $$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarFactura(in factId int)
	Begin 
		Delete from Facturas
			Where facturaId = factId;
	End $$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarFactura(in factId int)
	Begin
		Select
			Facturas.facturaId,
            Facturas.fecha,
            Facturas.hora,
            Facturas.clienteId,
            Facturas.empleadoId,
            Facturas.total
				From Facturas
                Where facturaId = factId;
	End $$
Delimiter ;

Delimiter $$
Create Procedure sp_editarFactura(in factId int, in fec date, in hor time, in cliId int, in empleId int, in tot int)
	Begin
		Update Facturas
			Set
				fecha = fec,
				hora = hor,
                clienteId = cliId,
                empleadoId = empId,
                total = tot
					Where facturaId = factId;
	End $$
Delimiter ;

-- ======================== Crud de ticketSporte ======================== --

Delimiter $$
Create Procedure sp_agregarTicketSoporte(in desTic varchar(250), in est varchar(30),in cliId int,in facId int) 
	Begin
			insert into ticketSoporte(descripcionTicket, estatus, clienteId, facturaId)
				values(desTic, est, cliId, facId);
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_listarTicketSoporte()
	Begin
			Select
				ticketSoporte.descripcionTicket,
				ticketSoporte.estatus,
				ticketSoporte.clienteId,
				ticketSoporte.facturaId
					From ticketSoporte;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_eliminarTicketSoporte(in tickSoportId int)
	Begin 
			Delete from ticketSoporte
				Where ticketSoporteId = tickSoportId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_buscarTicketSoporte(in tickSoportId int)
	Begin
			Select
				ticketSporte.descripcionTicket,
				ticketSporte.estatus,
				ticketSporte.clienteId,
				ticketSporte.facturaId
					From ticketSporte
					Where ticketSoporteId = tickSoportId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_editarTicketSoporte(in tickSoportId int, in desTic varchar(250), in est varchar(30), in cliId int, in facId int)
	Begin
			Update ticketSoporte
				Set
					descripcionTicket = desTic,
					estatus = est,
					clienteId = cliId,
					facturaId = factId
						Where ticketSoporteId = tickSoportId;
	End$$
Delimiter ;

-- ======================== Crud de DetalleFactur ======================== --

Delimiter $$
Create Procedure sp_agregarDetFacturas(in factId int, in prodcId int)
	Begin
		insert into DetalleFactura(facturaId, productoId)
			values(factId, prodcId);
	End$$
Delimiter ;
    
Delimiter $$
Create Procedure sp_ListarDetFacturas()
	Begin
		Select
			DetalleFactura.detalleFacturaId,
			DetalleFactura.facturaId,
            DetalleFactura.productoId
				From DetalleFactura;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_EliminarDetFacturas(in detaFactId int)
	Begin
			Delete from DetalleFactura
				Where detalleFacturaId = detaFactId;
	End $$
Delimiter ;

Delimiter $$
Create Procedure sp_BuscarDetFacturas(in detaFactId int)
	Begin
			Select
				DetalleFactura.detalleFacturaId,
				DetalleFactura.facturaId,
				DetalleFactura.productoId
					From DetalleFactura
					Where detalleFacturaId = detaFactId;
	End$$
Delimiter ;

Delimiter $$
Create Procedure sp_EditarDetFacturas(in detaFactId int, in factId int, in  prodcId int) 
	Begin
			Update DetalleFactura
				Set
				detalleFacturaId = detFacId,
				facturaId = factId,
				productoId = prodcId
						Where detalleFacturaId = detaFactId;
	End$$
Delimiter ;

-- ======================== Facturas Complement ======================== --

Delimiter $$
Create Procedure sp_asignarTotalFactura(in factId int, in totalFact decimal(10,2))
	Begin
		Update facturas 
			Set total = totalFact
				Where facturaId = factId;
	End $$
Delimiter ;

Delimiter $$
Create trigger tg_totalFactura
After insert on detalleFactura
For each row
	Begin
		Declare totalFact decimal(10,2);
     
		set totalFact = FN_totalFact(new.facturaId);
	End$$
Delimiter ;

-- ======================== Stok Complement ======================== --

Delimiter $$
Create Procedure sp_asignarDeStok(in proId int, in totalStok int)
	begin
		Update Productos 
			Set cantidad = totalStok
				Where productoId = proId;
	End $$
Delimiter ;


-- ======================== Empleado Complement ======================== --

Delimiter $$
Create Procedure sp_AsignarEncargado(in empId int,in encId int) 
	
    Begin
		Update Empleados 
			Set encargadoId = encId
					Where empleadoId = empId;
	End$$
Delimiter ;


