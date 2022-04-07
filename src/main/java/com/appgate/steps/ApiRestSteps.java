package com.appgate.steps;

import com.appgate.page.HomeRestApiClientPage;
import com.appgate.utils.Helpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiRestSteps {

    HomeRestApiClientPage homeRestApiClientPage;
    Helpers helpers;
    public void generarDatosServicioApp() {
        homeRestApiClientPage.seleccionarRequestMethod();
        homeRestApiClientPage.seleccionarProtocolo();
        homeRestApiClientPage.ingresarApiRest();
        homeRestApiClientPage.enviarRequestMethod();
    }

    public void validarCodeStatus200() {
        assertThat(homeRestApiClientPage.validarCodeStatus(),is("200"));
    }

    public void validarResponseGenerado() {
        homeRestApiClientPage.validarBody();
        assertThat(helpers.data.get("getLng"),containsString(helpers.data.get("Longitud")));
        assertThat(helpers.data.get("getLat"),containsString(helpers.data.get("Latitud")));
    }
}
