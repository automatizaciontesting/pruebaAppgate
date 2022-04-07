package com.appgate.stepsdefinition;

import com.appgate.steps.ApiRestSteps;
import com.appgate.utils.GoogleSpreadSheet;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class ApiRestStepsDefinition {
    @Steps
    ApiRestSteps apiRestSteps;
    @Steps
    GoogleSpreadSheet googleSpreadSheet;

    @Given("que se ingresan los datos del servicio (.*)$")
      public void queSeIngresanLosDatosDelServicio(Integer row, DataTable dataTable) {
        googleSpreadSheet.obtenerDataDrivenExcelOnline(row,dataTable);
        apiRestSteps.generarDatosServicioApp();
    }

    @Then("valido que los valores generados sean los correctos en el response")
    public void validoQueLosValoresGeneradosSeanLosCorrectosEnElResponse() {
        apiRestSteps.validarCodeStatus200();
        apiRestSteps.validarResponseGenerado();

    }
}
