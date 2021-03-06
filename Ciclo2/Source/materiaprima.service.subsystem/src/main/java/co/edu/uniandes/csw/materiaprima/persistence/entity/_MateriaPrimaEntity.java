
package co.edu.uniandes.csw.materiaprima.persistence.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class _MateriaPrimaEntity {

	@Id
	@GeneratedValue(generator = "MateriaPrima")
	private Long id;
	private String name;
	private Integer cantidadDisp;
	private Integer canitdadEnProc;
	private Integer tiempoEspera;
	private Double costoPromedio;
	private Integer cantidadMin;
	private Integer cantidadMax;

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public Integer getCantidadDisp(){
		return cantidadDisp;
	}
	
	public void setCantidadDisp(Integer cantidadDisp){
		this.cantidadDisp = cantidadDisp;
	}
	public Integer getCanitdadEnProc(){
		return canitdadEnProc;
	}
	
	public void setCanitdadEnProc(Integer canitdadEnProc){
		this.canitdadEnProc = canitdadEnProc;
	}
	public Integer getTiempoEspera(){
		return tiempoEspera;
	}
	
	public void setTiempoEspera(Integer tiempoEspera){
		this.tiempoEspera = tiempoEspera;
	}
	public Double getCostoPromedio(){
		return costoPromedio;
	}
	
	public void setCostoPromedio(Double costoPromedio){
		this.costoPromedio = costoPromedio;
	}
	public Integer getCantidadMin(){
		return cantidadMin;
	}
	
	public void setCantidadMin(Integer cantidadMin){
		this.cantidadMin = cantidadMin;
	}
	public Integer getCantidadMax(){
		return cantidadMax;
	}
	
	public void setCantidadMax(Integer cantidadMax){
		this.cantidadMax = cantidadMax;
	}
}