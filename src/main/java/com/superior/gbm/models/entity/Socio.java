package com.superior.gbm.models.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Entity
@Table(name="tbl_socios")
public class Socio implements Serializable {
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
	private String apellido;
	
	private String direccion;
	
	


	@NotEmpty
	@Email
	private String correo;
	@NotEmpty
	@Size(min = 8)
	private String password;

	private String Confirm;
	


	@NotEmpty
	@Pattern(regexp = "[0-9]{10}")
	private String celular;
	
	private Integer cuenta;
	private Integer estado;
	@NotEmpty
	private String empresa;
	@NotEmpty
	private String detalle;
	
	private String slider1;
	private String slider2;
	private String baner_slogan;
	private String baner_empresa;
	private String logo;
	private int calificacion;
	private int actualizar;
	private int codigo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	@ManyToOne
	@JoinColumn(name="ciudad_id")
	private Ciudad ciudad;
	
	@OneToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	
	
	
	

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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}
	public String getConfirm() {
		return Confirm;
	}

	public void setConfirm(String confirm) {
		Confirm = confirm;
	}






	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getSlider1() {
		return slider1;
	}

	public void setSlider1(String slider1) {
		this.slider1 = slider1;
	}

	public String getSlider2() {
		return slider2;
	}

	public void setSlider2(String slider2) {
		this.slider2 = slider2;
	}








	public String getBaner_slogan() {
		return baner_slogan;
	}

	public void setBaner_slogan(String baner_slogan) {
		this.baner_slogan = baner_slogan;
	}

	public String getBaner_empresa() {
		return baner_empresa;
	}

	public void setBaner_empresa(String baner_empresa) {
		this.baner_empresa = baner_empresa;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	




	public Integer getCuenta() {
		return cuenta;
	}

	public Integer getEstado() {
		return estado;
	}

	public Integer getCalificacion() {
		return calificacion;
	}

	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	
	
	

	public int getActualizar() {
		return actualizar;
	}

	public void setActualizar(int actualizar) {
		this.actualizar = actualizar;
	}
	
	

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	
	
		
	

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	
	
	
	
	
	@Override
	public String toString() {
		return "Socio [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion
				+ ", correo=" + correo + ", password=" + password + ", Confirm=" + Confirm + ", celular=" + celular
				+ ", cuenta=" + cuenta + ", estado=" + estado + ", empresa=" + empresa + ", detalle=" + detalle
				+ ", slider1=" + slider1 + ", slider2=" + slider2 + ", baner_slogan=" + baner_slogan
				+ ", baner_empresa=" + baner_empresa + ", logo=" + logo + ", calificacion=" + calificacion
				+ ", actualizar=" + actualizar + ", codigo=" + codigo + ", roles=" + roles + ", ciudad=" + ciudad
				+ ", categoria=" + categoria + "]";
	}
	
	
	


	

}
