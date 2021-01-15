package com.superior.gbm.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_categoria")
public class Categoria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long codigo;
	private String nombre;
	private int visible;
	private String detalle;
	private String imagenC;
	private String imagenC2;

	
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getVisible() {
		return visible;
	}
	public void setVisible(int visible) {
		this.visible = visible;
	}
	
	
	
	
	
	
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public String getImagenC() {
		return imagenC;
	}
	public void setImagenC(String imagenC) {
		this.imagenC = imagenC;
	}
	public String getImagenC2() {
		return imagenC2;
	}
	public void setImagenC2(String imagenC2) {
		this.imagenC2 = imagenC2;
	}
	
	
		
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Categoria [codigo=" + codigo + ", nombre=" + nombre + ", visible=" + visible + ", detalle=" + detalle
				+ ", imagenC=" + imagenC + ", imagenC2=" + imagenC2 + "]";
	}
	
	

}
