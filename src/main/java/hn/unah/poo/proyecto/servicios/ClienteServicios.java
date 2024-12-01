package hn.unah.poo.proyecto.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.ClienteDTO;
import hn.unah.poo.proyecto.dtos.PrestamosDTO;
import hn.unah.poo.proyecto.modelos.Cliente;
import hn.unah.poo.proyecto.modelos.Direccion;
import hn.unah.poo.proyecto.modelos.Prestamos;
import hn.unah.poo.proyecto.repositorios.ClienteRepositorio;
import hn.unah.poo.proyecto.repositorios.DireccionRepositorio;
import hn.unah.poo.proyecto.repositorios.PrestamosRepositorio;


@Service
public class ClienteServicios {

   
    private ModelMapper modelMapper;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private DireccionRepositorio direccionRepositorio;

    @Autowired
    private PrestamosRepositorio prestamosRepositorio;
    
    public String crearCliente(ClienteDTO clienteDTO){  
        if(this.clienteRepositorio.existsById(clienteDTO.getDni())){
            return "Ya existe el cliente";
        } else if (
        clienteDTO.getDireccionDTO()!=null && 
        clienteDTO.getPrestamosDTO().isEmpty())
        {
          Cliente cliente = new Cliente();
        cliente.setDni(clienteDTO.getDni());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setSueldo(clienteDTO.getSueldo());

        Direccion direccion = new Direccion();
        direccion.setPais(clienteDTO.getDireccionDTO().getPais());
        direccion.setDepartamento(clienteDTO.getDireccionDTO().getDepartamento());
        direccion.setCiudad(clienteDTO.getDireccionDTO().getCiudad());
        direccion.setColonia(clienteDTO.getDireccionDTO().getColonia());
        direccion.setReferencia(clienteDTO.getDireccionDTO().getReferencia());

        direccion.setCliente(cliente);
        direccionRepositorio.save(direccion);


        return "Cliente creado \n Direccion Agregada";
        } else if (
            clienteDTO.getDireccionDTO()!=null && 
            !clienteDTO.getPrestamosDTO().isEmpty()){
            Cliente cliente = new Cliente();
            cliente.setDni(clienteDTO.getDni());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setApellido(clienteDTO.getApellido());
            cliente.setTelefono(clienteDTO.getTelefono());
            cliente.setCorreo(clienteDTO.getCorreo());
            cliente.setSueldo(clienteDTO.getSueldo());
    
            Direccion direccion = new Direccion();
            direccion.setPais(clienteDTO.getDireccionDTO().getPais());
            direccion.setDepartamento(clienteDTO.getDireccionDTO().getDepartamento());
            direccion.setCiudad(clienteDTO.getDireccionDTO().getCiudad());
            direccion.setColonia(clienteDTO.getDireccionDTO().getColonia());
            direccion.setReferencia(clienteDTO.getDireccionDTO().getReferencia());

            List<Prestamos> listaPrestamos = new ArrayList<>();

            for (PrestamosDTO prestamosDTO : clienteDTO.getPrestamosDTO()) {
                modelMapper = new ModelMapper();
                listaPrestamos.add(modelMapper.map(prestamosDTO, Prestamos.class));

            }
            
            direccion.setCliente(cliente);
            direccionRepositorio.saveAndFlush(direccion);
            cliente.setPrestamos(listaPrestamos);
            prestamosRepositorio.saveAllAndFlush(listaPrestamos);
            clienteRepositorio.saveAndFlush(cliente);

            return "Cliente Agregado con direccion y lista de prestamos";
        }
        modelMapper = new ModelMapper();
        Cliente clientaSimple = modelMapper.map(clienteDTO, Cliente.class);
        clienteRepositorio.save(clientaSimple);
        return "Cliente Agregado";
    }

    public Cliente obtenerPorDni(String dni){
        modelMapper = new ModelMapper();
        Optional<Cliente> cliente = clienteRepositorio.findById(dni);
        if (cliente.isPresent()){
            Cliente clienteAgregar = cliente.get();
            return clienteAgregar;
        }
       return null;
    }

    public List<Cliente> obtenerTodos(){
        return clienteRepositorio.findAll();
}
}
