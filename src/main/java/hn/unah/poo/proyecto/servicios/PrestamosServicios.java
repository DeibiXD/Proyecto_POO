package hn.unah.poo.proyecto.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.PrestamosDTO;
import hn.unah.poo.proyecto.modelos.Prestamos;
import hn.unah.poo.proyecto.modelos.Tabla_Amortizacion;
import hn.unah.poo.proyecto.repositorios.AmortizacionRepositorio;
import hn.unah.poo.proyecto.repositorios.ClienteRepositorio;
import hn.unah.poo.proyecto.repositorios.PrestamosRepositorio;
import hn.unah.poo.proyecto.modelos.Cliente;

@Service
public class PrestamosServicios {
    
    @Autowired
    private PrestamosRepositorio prestamosRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AmortizacionRepositorio amortizacionRepositorio;

    ModelMapper modelMapper;

    @Value("${prestamos.tasa.vehicula}")
    private BigDecimal tasaVehicular;

    @Value("${prestamos.tasa.personal}")
    private BigDecimal tasaPersonal;

    @Value("${prestamos.tasa.hipotecario}")
    private BigDecimal tasaHipotecario;

   
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
        if(prestamosDTO.getPlazo()>=1 && (nivelEndeudamiento(dni).doubleValue()>0.4)){
        modelMapper = new ModelMapper();
        Prestamos prestamosParaAgregar = modelMapper.map(prestamosDTO, Prestamos.class);
        
        switch (prestamosDTO.getTipoPrestamo()) {
            case V:
                prestamosParaAgregar.setTasa_interes(tasaVehicular);
                break;
        
            case P:
                prestamosParaAgregar.setTasa_interes(tasaPersonal);
                break;

            case H:
                prestamosParaAgregar.setTasa_interes(tasaHipotecario);
        }
        prestamosParaAgregar.setCuota(calcularCuota(prestamosParaAgregar));
        //Ver si el cliente tiene buen nivel de endeudamiento
        
        //Logica para crear tabla de amortizacion
        List<Tabla_Amortizacion> creacionTabla = new ArrayList<>();
        int cuotasTotales = prestamosDTO.getPlazo()*12;
        //El primer registor
        Tabla_Amortizacion primerRegistro = new Tabla_Amortizacion();
        primerRegistro.setNumeroCuota(0);
        primerRegistro.setInteres(BigDecimal.ZERO);
        primerRegistro.setCapital(BigDecimal.ZERO);
        primerRegistro.setSaldo(prestamosDTO.getMonto());
        primerRegistro.setFechaVencimiento(LocalDate.now());
        primerRegistro.setEstado('A');
        primerRegistro.setPrestamos(prestamosParaAgregar);
        creacionTabla.add(primerRegistro);

        //El resto de los registros
        for (int i=0; i<cuotasTotales;i++){
            //Referencia al registor anterior
            Tabla_Amortizacion registroAnterior = creacionTabla.get(i);
            //Calculo de los atributos
            BigDecimal interes = prestamosParaAgregar.getTasa_interes();
            BigDecimal anio = new BigDecimal(12.0);
            interes= interes.divide(anio,2,RoundingMode.HALF_UP);
            interes = interes.multiply(registroAnterior.getSaldo());
            System.out.println(interes.toString());
            BigDecimal capital = prestamosParaAgregar.getCuota().subtract(interes);
            System.out.println(capital.toString());
            BigDecimal saldo = registroAnterior.getSaldo().subtract(capital);

            Tabla_Amortizacion nuevoRegistro = new Tabla_Amortizacion();
            nuevoRegistro.setCapital(capital);
            nuevoRegistro.setFechaVencimiento(registroAnterior.getFechaVencimiento().plusMonths(1));
            nuevoRegistro.setEstado('P');
            nuevoRegistro.setInteres(interes);
            nuevoRegistro.setNumeroCuota(i+1);
            nuevoRegistro.setSaldo(saldo);
            nuevoRegistro.setPrestamos(prestamosParaAgregar);
            creacionTabla.add(nuevoRegistro);
        }
        //Agregar tabla a prestamos
        prestamosParaAgregar.setTabla_amortizacion(creacionTabla);
        prestamosRepositorio.save(prestamosParaAgregar);
        
        if (clienteRepositorio.existsById(dni)){
            Cliente cliente = clienteRepositorio.findById(dni).get();
            if(cliente.getPrestamos()==null){
                cliente.setPrestamos(new ArrayList<>());
            }
            cliente.getPrestamos().add(prestamosParaAgregar);
            clienteRepositorio.save(cliente);
        }

        return "Agregado Correctamente";
    }
    return "No se pudo agregar este prestamo";
    }
    
    public BigDecimal calcularCuota(Prestamos prestamos){

        double P = prestamos.getMonto().doubleValue();
        double r = prestamos.getTasa_interes().doubleValue()/12.0;
        double n = ((double)prestamos.getPlazo())*12.0;

        double calculoCuota = (P*r*(Math.pow(1+r, n)))
        /(Math.pow(1+r, n)-1);

        return new BigDecimal(calculoCuota);
    }

    public BigDecimal nivelEndeudamiento(String dni){
        List<Prestamos> listaDePrestamosAsociadasAlCliente = prestamosRepositorio.findPrestamosByClienteDni(dni);
        BigDecimal totalEgresos = BigDecimal.ZERO;
        Cliente clienteParaCalcularSueldo= clienteRepositorio.findById(dni).orElseThrow(() -> new IllegalArgumentException("No existe el Cliente"));
        for (Prestamos prestamosAsociados : listaDePrestamosAsociadasAlCliente) {
            totalEgresos = totalEgresos.add(prestamosAsociados.getCuota());
        }
        BigDecimal nivelEndeudamiento = totalEgresos.divide(clienteParaCalcularSueldo.getSueldo());

        return nivelEndeudamiento;
    }

    public String agregarPrestamoExistente_A_Cliente(String dni, int idPrestamo) {
        if (clienteRepositorio.existsById(dni) && prestamosRepositorio.existsById(idPrestamo)){
            Cliente cliente = clienteRepositorio.findById(dni).get();
            Prestamos prestamos = prestamosRepositorio.findById(idPrestamo).get();
            if (cliente.getPrestamos()==null){
                cliente.setPrestamos(new ArrayList<>());
            }
            cliente.getPrestamos().add(prestamos);
            clienteRepositorio.save(cliente);
            return "Se agrego prestamo a cliente";
        }
        return "No existe una de las entidades";
    }

    public String sumarPendientesCuotasDelPrestamo(int id) {
        if(prestamosRepositorio.existsById(id)){
        List<Tabla_Amortizacion> listaAmortizacions = prestamosRepositorio.findById(id).get().getTabla_amortizacion();
        BigDecimal sumaTotal = BigDecimal.ZERO;
        for (Tabla_Amortizacion elementosTabla_Amortizacion : listaAmortizacions) {
            if (elementosTabla_Amortizacion.getEstado()=='P'){
                sumaTotal = sumaTotal.add(elementosTabla_Amortizacion.getCapital());
            }
        }
        return sumaTotal.toString();
    }
    return "Este prestamo no existe";
    }

    public String pagarUltimaCuota(int id) {
        if(prestamosRepositorio.existsById(id)){
            Prestamos prestamoAPagar = prestamosRepositorio.findById(id).get();
            List<Tabla_Amortizacion> tablaDeCuotas = prestamoAPagar.getTabla_amortizacion();
            Tabla_Amortizacion pagoMasReciente = tablaDeCuotas.stream()
            .filter(tA -> tA.getEstado()=='P')
            .max(Comparator.comparing(Tabla_Amortizacion::getFechaVencimiento).reversed())
            .orElse(null); 
            if (pagoMasReciente == null){
                return "No hay pagos pendientes";
            }
            pagoMasReciente.setEstado('A');
             amortizacionRepositorio.save(pagoMasReciente);
             return "Se pago con exito";
        }
        return "No encontramos el prestamo";
    }

    
}
