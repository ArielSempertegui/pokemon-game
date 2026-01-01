package com.ariel.model;

/**
 * POJO o Bean para los personajes del juego
 * @author Ariel Sempertegui
 *
 */
public class Personaje {

	private static final int NUM_TOTAL_ATAQUES_EVOLUCIONAR = 73; // Con setenta clicks de ataque aunque el personaje1 siga en el nivel 1 evolucionará
	
	private String nombre;
	private int vitalidad;
	private int ataque;
	private int defensa;
	
	public Personaje(String nombre, int vitalidad) {
		this.nombre = nombre;
		this.vitalidad = vitalidad;
	}

	public String getNombre() {
		return nombre;
	}
	
	public int getVitalidad() {
		return vitalidad;
	}

	public int getAtaque() {
		return ataque;
	}

	public int getDefensa() {
		return defensa;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setVitalidad(int vitalidad) {
		this.vitalidad = vitalidad;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	
	public boolean evoluciona(int numTotalAtaques) {
		
		return (numTotalAtaques == NUM_TOTAL_ATAQUES_EVOLUCIONAR);
	}
	
	public boolean haEvolucionado(int numTotalAtaques) {
		
		return (numTotalAtaques >= NUM_TOTAL_ATAQUES_EVOLUCIONAR);
	}

}

