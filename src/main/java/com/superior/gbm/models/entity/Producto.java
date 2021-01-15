package com.superior.gbm.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

@Entity
@Table(name="tbl_producto")
public class Producto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String detalle;
	@NotNull
	private int precio;
	
	@ManyToOne
	@JoinColumn(name="categoria_codigo")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name="socio_id")
	private Socio socio;
	
	
	private String baner_producto;
	private String imagen_producto;
	private int calificacion;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	
	
	public String getBaner_producto() {
		return baner_producto;
	}
	public void setBaner_producto(String baner_producto) {
		this.baner_producto = baner_producto;
	}
	public String getImagen_producto() {
		return imagen_producto;
	}
	public void setImagen_producto(String imagen_producto) {
		this.imagen_producto = imagen_producto;
	}
	
	
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", detalle=" + detalle + ", precio=" + precio
				+ ", categoria=" + categoria + ", socio=" + socio + ", baner_producto=" + baner_producto
				+ ", imagen_producto=" + imagen_producto + ", calificacion=" + calificacion + "]";
	}
	
	
	
	
	
}