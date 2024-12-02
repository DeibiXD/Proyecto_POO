package hn.unah.poo.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.unah.poo.proyecto.dtos.PrestamosDTO;
import hn.unah.poo.proyecto.modelos.Prestamos;
import hn.unah.poo.proyecto.servicios.PrestamosServicios;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/prestamos")
@Tag(name = "Prestamos", description = "Metodos relacionados con Prestamos")
public class PrestamosControlador {
    
    @Autowired
    private PrestamosServicios prestamosServicios;

    @PostMapping("/crear")
    @Operation(summary = "Crea un nuevo Prestamo", description = "Crea un nuevo prestamo con todos su datos")
    public String crearPrestamos(@RequestBody PrestamosDTO prestamosDTO, @RequestParam(name = "dni",defaultValue = "0")String dni) {
        return prestamosServicios.crearPrestamos(prestamosDTO,dni);
    }

    @GetMapping("/buscar/dni")
    @Operation(summary = "Obtiene un prestamo por su numero de DNI de su cliente", description = "Este retorna todos los detalles de nuestro Prestamo el cual buscamos por su DNI del cliente")
    public List<Prestamos> buscarPorDni(@RequestParam(name = "dni") String dni) {
        return prestamosServicios.buscarPorDni(dni);
    }

    @GetMapping("/buscar/id")
    @Operation(summary = "Obtiene el prestamo por su ID", description = "Damos el Id del prestamo que queremos obtener")
    public Prestamos buscarPorId(@RequestParam(name = "id") int id) {
        return prestamosServicios.buscarPorId(id);
    }
    
    @PostMapping("/agregar/cliente")
    @Operation(summary = "Con este metodo agregamos un pretamo a un Cliente", description = "Un tipo de prestamo es agregado a nuestro Cliente ya registrado en nuestra bas de datos.")
    public String agregarPrestamoExistente_A_Cliente(
        @RequestParam(name="dni") String dni,
        @RequestParam(name="id")int idPrestamo) {
        
        return prestamosServicios.agregarPrestamoExistente_A_Cliente(dni,idPrestamo) ;
    }
    
    @GetMapping("/obtener/saldo")
    @Operation(summary = "Obtenemos el saldo sumando las cuotas pendientes de su prestamo", description = "Devuelve el saldo con todos las cuotas pendientes a pagar")
    public String sumarPendientesCuotasDelPrestamo(@RequestParam(name = "id") int id) {
        return prestamosServicios.sumarPendientesCuotasDelPrestamo(id);
    }
    
    @PostMapping("/pagar/cuota")
    @Operation(summary = "Metodo para pagar la cuota siguiente", description = "Obtiene la ultima cuota a pagar y la cancela.")
    public String pagarUltimaCuota(@RequestParam(name = "id")int id){
        return prestamosServicios.pagarUltimaCuota(id);
    }
    
}
