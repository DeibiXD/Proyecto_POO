package hn.unah.poo.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hn.unah.poo.proyecto.modelos.Prestamos;

public interface PrestamosRepositorio extends JpaRepository<Prestamos,Integer>{

      @Query("SELECT p FROM Prestamos p JOIN p.clientes c WHERE c.dni = :dni")
    List<Prestamos> findPrestamosByClienteDni(@Param("dni") String dni);
    
}
