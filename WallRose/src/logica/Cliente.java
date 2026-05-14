package logica;

import java.util.Map;
import java.util.TreeMap;

public class Cliente {
	private final String id;
	private String Nombre;
	private String email;
	private Map<Integer, Orden> ordenes;
	
	public Cliente(String id, String nombre, String email) {
		this.id = id;
		this.Nombre = nombre;
		this.email = email;
		this.ordenes = new TreeMap<Integer, Orden>();
	}

	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public Map<Integer, Orden> getOrdenes() {
		return ordenes;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	void agregarOrden(Integer llave, Orden orden) {
		ordenes.put(llave, orden);
	}
	void borrarOrden(Integer llave, Orden orden) throws Exception{
		if (!ordenes.containsValue(orden)) {
			throw new Exception("Orden no encontrada.");
		}
		ordenes.remove(llave, orden);
	}
}
