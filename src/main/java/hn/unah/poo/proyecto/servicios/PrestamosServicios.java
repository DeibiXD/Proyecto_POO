package hn.unah.poo.proyecto.servicios;

import java.util.List;

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

   
    public List<Prestamos> buscarPorDni(String dni) {
        return prestamosRepositorio.findPrestamosByClienteDni(dni);
    }

    public Prestamos buscarPorId(int id) {

        if (prestamosRepositorio.existsById(id)){
            return prestamosRepositorio.findById(id).get();
        }
        return null;

    }

    public String crearPrestamos(PrestamosDTO prestamosDTO, String dni) {
        modelMapper = new ModelMapper();
        Prestamos prestamosParaAgregar = modelMapper.map(prestamosDTO, Prestamos.class);
        prestamosRepositorio.saveAndFlush(prestamosParaAgregar);
        if (clienteRepositorio.existsById(dni)){
            Cliente cliente = clienteRepositorio.findById(dni).get();
            cliente.getPrestamos().add(prestamosParaAgregar);
            clienteRepositorio.save(cliente);
        }
        return "Agregado Correctamente";
    }

    public String agregarPrestamoExistente_A_Cliente(String dni, int idPrestamo) {
        if (clienteRepositorio.existsById(dni) && prestamosRepositorio.existsById(idPrestamo)){
            Cliente cliente = clienteRepositorio.findById(dni).get();
            Prestamos prestamos = prestamosRepositorio.findById(idPrestamo).get();
            cliente.getPrestamos().add(prestamos);
            clienteRepositorio.save(cliente);
            return "Se agrego prestamo a cliente";
        }
        return "No existe una de las entidades";
    }


    
}
