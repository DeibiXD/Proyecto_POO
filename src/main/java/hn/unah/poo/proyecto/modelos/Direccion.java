package hn.unah.poo.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "direccion")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Direccion {
   
    @Id
    @Column(name = "iddireccion")
    private int idDireccion;

    private String pais;

    private String departamento;

    private String ciudad;

    private String colonia;

    private String referencia;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "dni", referencedColumnName = "dni")
    @JsonIgnore
    private Cliente cliente;

    

}
