package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.model.Odontologo;
import org.apache.log4j.Logger;

import java.util.List;


public class OdontologoDaoMemoria implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoMemoria.class);
    private List<Odontologo> odontologoRepository;

    public OdontologoDaoMemoria(List<Odontologo> odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        int id = odontologoRepository.size() + 1;
        odontologoRepository.add(odontologo);
        Odontologo odontologoGuardado = new Odontologo(id, odontologo.getNumeroMatricula(), odontologo.getNombre(), odontologo.getApellido());
        LOGGER.info("Odontologo guardado: " + odontologoGuardado);
        return odontologo;
    }


    @Override
    public Odontologo buscarPorId(int id) {
        Odontologo odontologoBuscado = odontologoRepository.get(id - 1);
        if (odontologoBuscado != null)
            LOGGER.info("El odontologo de id " + id + " ha sido encontrado: " + odontologoBuscado);
        else LOGGER.info("Odontologo no encontrado");
        return odontologoBuscado;
    }

    public List<Odontologo> getOdontologoRepository() {
        return odontologoRepository;
    }
}


