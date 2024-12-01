package hn.unah.poo.proyecto.servicios;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.PrestamosDTO;
import hn.unah.poo.proyecto.modelos.Prestamos;
import hn.unah.poo.proyecto.repositorios.ClienteRepositorio;
import hn.unah.poo.proyecto.repositorios.PrestamosRepositorio;
import hn.unah.poo.proyecto.modelos.Cliente;

@Service
public class PrestamosServicios {
    
    @Autowired
    private PrestamosRepositorio prestamosRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    ModelMapper modelMapper;

   
    public Prestamos buscarPorDni(String dni) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorDni'");
    }

    public Prestamos buscarPorId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    public String crearPrestamos(PrestamosDTO prestamosDTO) {
        modelMapper = new ModelMapper();
        Prestamos prestamosParaAgregar = modelMapper.map(prestamosDTO, Prestamos.class);
        prestamosRepositorio.save(prestamosParaAgregar);
        return "Agregado Correctamente";
    }

    public String agregarPrestamoExistente_A_Cliente(String dni, int idPrestamo) {
        if (clienteRepositorio.existsById(dni) && prestamosRepositorio.existsById(idPrestamo)){
            Optional<Cliente> O_Cliente = clienteRepositorio.findById(dni);
            Cliente cleinteParaAgregar = O_Cliente.get();
            return null;
        }
        return null;
    }


    
}
