package com.backend.parcial.test;


import com.backend.parcial.dao.impl.OdontologoDaoH2;
import com.backend.parcial.dao.impl.OdontologoDaoMemoria;
import com.backend.parcial.model.Odontologo;
import com.backend.parcial.service.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class OdontologoServiceTest {

    private OdontologoService odontologoService;

    @BeforeAll
    static void doBefore() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/caraballojoseryn_moleseduardo;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    void deberiaAgregarYRetornarIdDeMedicamentoEnH2() {
        odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologoAPersistir = new Odontologo(001, 1987, "Eduardo", "Morles");

        Odontologo odontologoPersistido = odontologoService.registrarOdontologo(odontologoAPersistir);

        assertNotNull(odontologoPersistido.getId());

    }

    @Test
    void deberiaAgregarYRetornarIdDeOdontologoEnMemoria() {
        odontologoService = new OdontologoService(new OdontologoDaoMemoria(new ArrayList<>()));
        Odontologo odontologoAPersistir = new Odontologo(001, 1987, "Eduardo", "Morles");
        Odontologo odontologoPersistido = odontologoService.registrarOdontologo(odontologoAPersistir);
        assertNotNull(odontologoPersistido.getId());

    }


}





