package hn.unah.poo.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "direccion")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Direccion {
   
    @Id
    @Column(name = "iddireccion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDireccion;

    private String pais;

    private String departamento;

    private String ciudad;

    private String colonia;

    private String referencia;

    @ManyToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "dni", referencedColumnName = "dni")
    @JsonIgnore
    private Cliente cliente;

    

}
