/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juald
 */
@Entity
@Table(name = "ACTIVIDAD")
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByIdactividad", query = "SELECT a FROM Actividad a WHERE a.idactividad = :idactividad"),
    @NamedQuery(name = "Actividad.findByNombre", query = "SELECT a FROM Actividad a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Actividad.findByDescripcion", query = "SELECT a FROM Actividad a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Actividad.findByPreciobasemes", query = "SELECT a FROM Actividad a WHERE a.preciobasemes = :preciobasemes")})
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDACTIVIDAD")
    private String idactividad;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "PRECIOBASEMES")
    private Integer preciobasemes;
    @JoinTable(name = "REALIZA", joinColumns = {
        @JoinColumn(name = "IDACTIVIDAD", referencedColumnName = "IDACTIVIDAD")}, inverseJoinColumns = {
        @JoinColumn(name = "NUMEROSOCIO", referencedColumnName = "NUMEROSOCIO")})
    @ManyToMany
    private Set<Socio> socios = new HashSet<Socio>();
    @JoinColumn(name = "MONITORRESPONSABLE", referencedColumnName = "CODMONITOR")
    @ManyToOne
    private Monitor monitorresponsable;

    /**
     *
     */
    public Actividad() {
    }

    /**
     *
     * @param idactividad
     */
    public Actividad(String idactividad) {
        this.idactividad = idactividad;
    }

    /**
     *
     * @param idactividad
     * @param nombre
     * @param descripcion
     * @param preciobasemes
     */
    public Actividad(String idactividad, String nombre, String descripcion, Integer preciobasemes) {
        this.idactividad = idactividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preciobasemes = preciobasemes;
    }

    /**
     *
     * @return
     */
    public String getIdactividad() {
        return idactividad;
    }

    /**
     *
     * @param idactividad
     */
    public void setIdactividad(String idactividad) {
        this.idactividad = idactividad;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public Integer getPreciobasemes() {
        return preciobasemes;
    }

    /**
     *
     * @param preciobasemes
     */
    public void setPreciobasemes(Integer preciobasemes) {
        this.preciobasemes = preciobasemes;
    }

    /**
     *
     * @return
     */
    public Set<Socio> getSocio() {
        return socios;
    }

    /**
     *
     * @param socios
     */
    public void setSocioSet(Set<Socio> socios) {
        this.socios = socios;
    }

    /**
     *
     * @return
     */
    public Monitor getMonitorresponsable() {
        return monitorresponsable;
    }

    /**
     *
     * @param monitorresponsable
     */
    public void setMonitorresponsable(Monitor monitorresponsable) {
        this.monitorresponsable = monitorresponsable;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idactividad != null ? idactividad.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.idactividad == null && other.idactividad != null) || (this.idactividad != null && !this.idactividad.equals(other.idactividad))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Modelo.Actividad[ idactividad=" + idactividad + " ]";
    }

    /**
     *
     * @param socio
     */
    public void addSocio(Socio socio) {
        socios.add(socio);
        socio.getActividadSet().add(this);
    }

    /**
     *
     * @param socio
     */
    public void eliminaSocio(Socio socio) {
        socios.remove(socio);
        socio.getActividadSet().remove(this);
    }

    /**
     *
     * @param defecto
     */
    public void actualizaMonitor(Monitor defecto) {
        this.monitorresponsable = defecto;
    }

}
