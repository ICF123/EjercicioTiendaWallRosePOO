package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Orden {
	private final Integer numero;
	private final LocalDateTime fecha;
	private EstadoOrden estado;
	private List<LineaOrden> lineas;
	private Cliente cliente;
	private final static Double IV = 0.13;
	
	public Orden(Integer numero, Cliente cliente) {
		this.cliente = cliente;
		this.numero = numero;
		this.fecha = LocalDateTime.now();
		this.estado = EstadoOrden.INICIADA;
		this.lineas = new ArrayList<LineaOrden>();
	}

	public EstadoOrden getEstado() {
		return estado;
	}
	public void setEstado(EstadoOrden estado) {
		this.estado = estado;
	}

	public Integer getNumero() {
		return numero;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public List<LineaOrden> getLineas() {
		return lineas;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public Double calcularMonto(Integer pos) {
		Double monto = lineas.get(pos).calcularCosto();
		return monto;
	}
	public Double calcularMontoImpuesto(Integer pos) {
		Double montoImpuesto = calcularMonto(pos) * IV;
		return montoImpuesto;
	}
	public Double calcularMontoTotal() {
		Double monto = 0.0;
		for (int i = 0; i < lineas.size(); i++) {
			monto += lineas.get(i).calcularCosto();
		}
		return monto * IV;
	}
	public void agregarLinea(LineaOrden linea) {
		lineas.add(linea);
	}
	public void borrearLinea(Integer numeroLinea) {
		lineas.remove(numeroLinea);
	}
}
