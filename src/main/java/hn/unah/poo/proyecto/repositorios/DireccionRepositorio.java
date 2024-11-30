package hn.unah.poo.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.unah.poo.proyecto.modelos.Direccion;

@Repository
public interface DireccionRepositorio extends JpaRepository<Direccion,Integer>{
    
}