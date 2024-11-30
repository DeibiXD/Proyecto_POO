package hn.unah.poo.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.unah.poo.proyecto.modelos.Prestamos;

public interface PrestamosRepositorio extends JpaRepository<Prestamos,Integer>{

    
}
