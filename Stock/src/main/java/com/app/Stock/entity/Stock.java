package com.app.Stock.entity;

<<<<<<< HEAD
import jakarta.persistence.Column;
=======
import java.time.LocalDateTime;
>>>>>>> parte-sab
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
<<<<<<< HEAD
@Table(name="cursos")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String materia;
	private String nombreMaestro;
	private int numSalon;
	
	@Column(name = "administracion_id")
	private long administracionId;
	
	public Stock() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getNombreMaestro() {
		return nombreMaestro;
	}

	public void setNombreMaestro(String nombreMaestro) {
		this.nombreMaestro = nombreMaestro;
	}

	public int getNumSalon() {
		return numSalon;
	}

	public void setNumSalon(int numSalon) {
		this.numSalon = numSalon;
	}
	public long getAdministracionId() {
		return administracionId;
	}

	public void setAdministracionId(long administracionId) {
		this.administracionId = administracionId;
	}

	
=======
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productoId;
    private Integer cantidad;
    private LocalDateTime fechaActualizacion;

    public Stock() {
    }

    public Stock(Integer id, Integer productoId, Integer cantidad, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
>>>>>>> parte-sab
}