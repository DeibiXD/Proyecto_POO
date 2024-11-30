package hn.unah.poo.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.unah.poo.proyecto.modelos.Tabla_Amortizacion;

public interface AmortizacionRepositorio extends JpaRepository<Tabla_Amortizacion,Integer>{  
}
