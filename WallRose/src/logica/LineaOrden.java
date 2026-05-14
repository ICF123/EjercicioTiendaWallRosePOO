package logica;

public class LineaOrden {
	private Double cantidad;
	private Producto producto;
	
	public LineaOrden(Double cantidad, Producto producto) {
		this.cantidad = cantidad;
		this.producto = producto;
	}

	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public Double calcularCosto() {
		Double costo = producto.getPrecio() + producto.getExistencias();
		return costo;
	}
}
