package hn.unah.poo.proyecto.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.ClienteDTO;
import hn.unah.poo.proyecto.dtos.DireccionDTO;
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

    @Autowired
    private PrestamosServicios prestamosServicios;
    
    public String crearCliente(ClienteDTO clienteDTO){  
        if(this.clienteRepositorio.existsById(clienteDTO.getDni())){
            return "Ya existe el cliente";
        } else if (
            //Hay direccion no hay prestamos
        !(clienteDTO.getDireccionDTO()==null) && 
        clienteDTO.getPrestamosDTO()==null &&
        clienteDTO.getDireccionDTO().size()<=2) 
        {
          Cliente cliente = new Cliente();
        cliente.setDni(clienteDTO.getDni());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setSueldo(clienteDTO.getSueldo());

        List<Direccion> direccion = new ArrayList<>();
        
            for (DireccionDTO direccionEnArray : clienteDTO.getDireccionDTO()) {
                Direccion direccionFinal = new Direccion();
                direccionFinal.setPais(direccionEnArray.getPais());
                direccionFinal.setDepartamento(direccionEnArray.getDepartamento());
                direccionFinal.setCiudad(direccionEnArray.getCiudad());
                direccionFinal.setColonia(direccionEnArray.getColonia());
                direccionFinal.setReferencia(direccionEnArray.getReferencia());
                direccionFinal.setCliente(cliente);
                direccion.add(direccionFinal);
            }
        
        direccionRepositorio.saveAll(direccion);

        return "Cliente creado \n Direccion Agregada";
        } else if (
            !(clienteDTO.getDireccionDTO()==null)&& 
            !(clienteDTO.getPrestamosDTO()==null) &&
            clienteDTO.getDireccionDTO().size()<=2){
            Cliente cliente = new Cliente();
            cliente.setDni(clienteDTO.getDni());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setApellido(clienteDTO.getApellido());
            cliente.setTelefono(clienteDTO.getTelefono());
            cliente.setCorreo(clienteDTO.getCorreo());
            cliente.setSueldo(clienteDTO.getSueldo());
    
            List<Direccion> direccion = new ArrayList<>();
        
            for (DireccionDTO direccionEnArray : clienteDTO.getDireccionDTO()) {
                Direccion direccionFinal = new Direccion();
                direccionFinal.setPais(direccionEnArray.getPais());
                direccionFinal.setDepartamento(direccionEnArray.getDepartamento());
                direccionFinal.setCiudad(direccionEnArray.getCiudad());
                direccionFinal.setColonia(direccionEnArray.getColonia());
                direccionFinal.setReferencia(direccionEnArray.getReferencia());
                direccionFinal.setCliente(cliente);
                direccion.add(direccionFinal);
            }
            cliente.setDireccion(direccion);
            clienteRepositorio.saveAndFlush(cliente);
            for (PrestamosDTO prestamosDTO : clienteDTO.getPrestamosDTO()) {
                prestamosServicios.crearPrestamos(prestamosDTO, cliente.getDni());
            }
            return "Cliente Agregado con direccion y lista de prestamos";
        } else if (clienteDTO.getDireccionDTO().size()> 2){
            return "Un cliente solo puede tener 2 direcciones";
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
