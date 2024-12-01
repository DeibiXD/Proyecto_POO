package hn.unah.poo.proyecto.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.PrestamosDTO;
import hn.unah.poo.proyecto.modelos.Prestamos;
import hn.unah.poo.proyecto.repositorios.PrestamosRepositorio;

@Service
public class PrestamosServicios {
    
    @Autowired
    private PrestamosRepositorio prestamosRepositorio;

    public String crearPrestamos(PrestamosDTO prestamosDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crearPrestamos'");
    }

    public Prestamos buscarPorDni(String dni) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorDni'");
    }

    public Prestamos buscarPorId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    
}
