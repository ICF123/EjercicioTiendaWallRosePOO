package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logica.Cliente;
import logica.EstadoOrden;
import logica.LineaOrden;
import logica.Orden;
import logica.Producto;

public class ControladoraWallRose {
	private static ControladoraWallRose instance = null;
	private Integer consecutivoOrden;
	private Integer consecutivoProducto;
	private Map<String, Cliente> clientes;
	private Map<Integer, Orden> ordenes;
	private Map<Integer, Producto> productos;
	
	private ControladoraWallRose() {
		this.consecutivoOrden = 1;
		this.consecutivoProducto = 1;
		this.clientes = new TreeMap<String, Cliente>();
		this.ordenes = new TreeMap<Integer, Orden>();
		this.productos = new TreeMap<Integer, Producto>();
	}
	public static ControladoraWallRose getInstance() {
		if (instance == null)
			instance = new ControladoraWallRose();
		return instance;
	}
	public List<Cliente> obtenerListadoClientes() {
		List<Cliente> listadoClientes = new ArrayList<Cliente>();
		for (Map.Entry<String, Cliente> e : clientes.entrySet()) {
			listadoClientes.add(e.getValue());
		}
		return listadoClientes;
	}
	private void verificarClienteExistente(String idCliente) throws Exception {
		if (!clientes.containsKey(idCliente))
			throw new Exception("Cliente no encontrado.");
	}
	public Cliente obtenerCliente(String idCliente) throws Exception {
		verificarClienteExistente(idCliente);
		return clientes.get(idCliente);
	}
	public List<Orden> obtenerListadoOrdenesCliente(String idCliente) throws Exception {
		verificarClienteExistente(idCliente);
		Cliente c = clientes.get(idCliente);
		List<Orden> listadoOrdenes = new ArrayList<Orden>();
		Map<Integer, Orden> ordenes = c.getOrdenes();
		for (Orden o : ordenes.values()) {
			listadoOrdenes.add(o);
		}
		return listadoOrdenes;
	}
	private List<Orden> obtenerListadoOrdenesPorEstado(String idCliente, EstadoOrden estado) {
		List<Orden> listadoOrdenes = new ArrayList<Orden>();
		for (Map.Entry<Integer, Orden> entry : ordenes.entrySet()) {
			Orden orden = entry.getValue();
			if (orden.getEstado() == estado)
				listadoOrdenes.add(orden);
		}
		return listadoOrdenes;
	}
	public List<Orden> obtenerListadoOrdenesIniciadasCliente(String idCliente) throws Exception {
		verificarClienteExistente(idCliente);
		return obtenerListadoOrdenesPorEstado(idCliente, EstadoOrden.INICIADA);
	}
	public List<Orden> obtenerListadoOrdenesPendientesCliente(String idCliente) throws Exception {
		verificarClienteExistente(idCliente);
		return obtenerListadoOrdenesPorEstado(idCliente, EstadoOrden.PENDIENTE);
	}
	public List<Orden> obtenerListadoOrdenesTerminadasCliente(String idCliente) throws Exception {
		verificarClienteExistente(idCliente);
		return obtenerListadoOrdenesPorEstado(idCliente, EstadoOrden.TERMINADA);
	}
	private boolean esEmailValido(String email) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	public void crearCliente(String idCliente, String nombre, String email) throws Exception {
		if (clientes.containsKey(idCliente))
			throw new Exception("Cliente ya existe.");
		if (!esEmailValido(email))
			throw new Exception("No es email valido.");
		Cliente c = new Cliente(idCliente, nombre, email);
		clientes.put(idCliente, c);
	}
	public void actualizarCliente(String idCliente, String nombre, String email) throws Exception {
		verificarClienteExistente(idCliente);
		if (!esEmailValido(email))
			throw new Exception("No es email valido.");
		Cliente c = clientes.get(idCliente);
		c.setEmail(email);
		c.setNombre(nombre);
		clientes.replace(idCliente, c);
	}
	public void borrarCliente(String idCliente) throws Exception {
		verificarClienteExistente(idCliente);
		clientes.remove(idCliente);
	}
	public List<Producto> obtenerListadoProductos() {
		List<Producto> listadoProductos = new ArrayList<Producto>();
		for (Map.Entry<Integer, Producto> e : productos.entrySet()) {
			listadoProductos.add(e.getValue());
		}
		return listadoProductos;
	}
	private void verificarProductoExistente(Integer codigoProducto) throws Exception {
		if (!productos.containsKey(codigoProducto))
			throw new Exception("Producto no encontrado.");
	}
	public void crearProducto(Integer codigoProducto, String nombre, Double existencias, String unidad, Double precio) throws Exception {
		if (productos.containsKey(codigoProducto))
			throw new Exception("Producto ya existe.");
		Producto p = new Producto(codigoProducto, nombre, existencias, unidad, precio);
		productos.put(codigoProducto, p);
	}
	public Producto obtenerProducto(Integer codigoProducto) throws Exception {
		verificarProductoExistente(codigoProducto);
		return productos.get(codigoProducto);
	}
	public void actualizarProducto(Integer codigoProducto, String nombre, Double existencias, String unidad, Double precio) throws Exception {
		verificarProductoExistente(codigoProducto);
		Producto p = productos.get(codigoProducto);
		p.setNombre(nombre);
		p.setExistencias(existencias);
		p.setPrecio(precio);
		p.setUnidad(unidad);
		productos.replace(codigoProducto, p);
	}
	public void borrarProducto(Integer codigoProducto) throws Exception {
		verificarProductoExistente(codigoProducto);
		productos.remove(codigoProducto);
	}
	public List<Orden> obtenerListadoOrdenes() {
		List<Orden> listadoOrdenes = new ArrayList<Orden>();
		for (Map.Entry<Integer, Orden> entry : ordenes.entrySet()) {
			listadoOrdenes.add(entry.getValue());
		}
		return listadoOrdenes;
	}
	public Double obtenerMontoTotalPendiente() {
		Double monto = 0.0;
		for (Map.Entry<Integer, Orden> entry : ordenes.entrySet()) {
			Orden p = entry.getValue();
			monto += p.calcularMontoTotal();
		}
		return monto;
	}
	private void verificarOrdenExistente(Integer numeroOrden) throws Exception {
		if (!productos.containsKey(numeroOrden))
			throw new Exception("Producto no encontrado.");
	}
	void crearOrdenVacia(Integer numeroOrden, String idCliente) throws Exception {
		verificarClienteExistente(idCliente);
		verificarOrdenExistente(numeroOrden);
		Cliente c = clientes.get(idCliente);
		Orden o = new Orden(numeroOrden, c);
		ordenes.put(numeroOrden, o);
	}
	public Orden obtenerOrden(Integer numeroOrden) throws Exception {
		verificarOrdenExistente(numeroOrden);
		return ordenes.get(numeroOrden);
	}
	public List<LineaOrden> obtenerLineaOrden(Integer numeroOrden) throws Exception {
		verificarOrdenExistente(numeroOrden);
		Orden o = ordenes.get(numeroOrden);
		List<LineaOrden> lineas = o.getLineas();
		return lineas;
	}
	public void establecerOrdenPendiente(Integer numeroOrden) throws Exception {
		verificarOrdenExistente(numeroOrden);
		Orden o = ordenes.get(numeroOrden);
		o.setEstado(EstadoOrden.PENDIENTE);
		ordenes.replace(numeroOrden, o);
	}
	public void establecerOrdenTerminada(Integer numeroOrden) throws Exception {
		verificarOrdenExistente(numeroOrden);
		Orden o = ordenes.get(numeroOrden);
		o.setEstado(EstadoOrden.TERMINADA);
		ordenes.replace(numeroOrden, o);
	}
	public void agregarLineaOrden(Integer numeroOrden, Integer codigoProducto, Double cantidad) throws Exception {
		verificarOrdenExistente(numeroOrden);
		verificarProductoExistente(codigoProducto);
		Orden o = ordenes.get(numeroOrden);
		Producto p = productos.get(codigoProducto);
		LineaOrden lo = new LineaOrden(cantidad, p);
		o.agregarLinea(lo);
		ordenes.replace(numeroOrden, o);
	}
	public void actualizarLineaOrden(Integer numeroOrden, Integer numeroLinea, Integer codigoProducto, Double cantidad) throws Exception {
		verificarOrdenExistente(numeroOrden);
		verificarProductoExistente(codigoProducto);
		Orden o = ordenes.get(numeroOrden);
		List<LineaOrden> llo = o.getLineas();
		if (numeroLinea > llo.size() || numeroLinea < 0)
			throw new Exception("Numero de linea invalido.");
		Producto p = productos.get(codigoProducto);
		LineaOrden lo = new LineaOrden(cantidad, p);
		llo.remove(numeroLinea);
		llo.add(numeroLinea, lo);
	}
	public void borrarLineaOrden(Integer numeroOrden, Integer numeroLinea) throws Exception {
		verificarOrdenExistente(numeroOrden);
		Orden o = ordenes.get(numeroOrden);
		List<LineaOrden> llo = o.getLineas();
		if (numeroLinea > llo.size() || numeroLinea < 0)
			throw new Exception("Numero de linea invalido.");
		llo.remove(numeroLinea);
	}
	public void borrarOrden(Integer numeroOrden) throws Exception{
		verificarOrdenExistente(numeroOrden);
		ordenes.remove(numeroOrden);
	}
}
