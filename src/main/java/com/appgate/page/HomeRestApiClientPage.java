package com.appgate.page;

import com.appgate.utils.Helpers;
import com.appgate.utils.MobilePageObject;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class HomeRestApiClientPage extends MobilePageObject {
    /*** para sobre-escribir el driver*/
    public HomeRestApiClientPage(WebDriver driver){
        super(driver);
    }

    Helpers helpers;
    @FindBy(xpath="//android.widget.TextView[@text='REQUEST']")
    WebElement btnRequestMethod;
    @FindBy(xpath="//android.widget.Spinner[@resource-id='com.sn.restandroid:id/spinnerHttp']")
    WebElement cmbProtocolo;
    @FindBy(xpath="//android.widget.TextView[@text='http://']")
    WebElement lnkProtocoloHttp;
    @FindBy(xpath="//android.widget.TextView[@text='https://']")
    WebElement lnkProtocoloHttps;
    @FindBy(xpath="//android.widget.EditText[@resource-id='com.sn.restandroid:id/editTextUrl']")
    WebElement txtUrlApiClient;
    @FindBy(xpath="//android.widget.Button[@resource-id='com.sn.restandroid:id/buttonRequestSend']")
    WebElement btnSend;
    @FindBy(xpath="//android.widget.TextView[@text='200']")
    WebElement lblCodeStatus;
    @FindBy(xpath="//android.view.View")
    List<WebElement> lblGetBody;



    public void seleccionarRequestMethod() {
        Serenity.takeScreenshot();
        btnRequestMethod.click();
    }

    public void seleccionarProtocolo() {
        Serenity.takeScreenshot();
        cmbProtocolo.click();
        if(helpers.data.get("Protocolo").equals("http")){
            Serenity.takeScreenshot();
            lnkProtocoloHttp.click();
        }else if(helpers.data.get("Protocolo").equals("https")){
            Serenity.takeScreenshot();
            lnkProtocoloHttps.click();
        }

    }

    public void ingresarApiRest() {
        withTimeoutOf(Duration.ofSeconds(15)).waitFor(txtUrlApiClient);
        txtUrlApiClient.sendKeys("api.geonames.org/timezoneJSON?formatted=true&lat="+helpers.data.get("Latitud")+"&lng="+helpers.data.get("Longitud")+"&username="+helpers.data.get("UserName")+"&style=full");
        Serenity.takeScreenshot();
    }

    public void enviarRequestMethod() {
        btnSend.click();
        Serenity.takeScreenshot();
    }

    public void validarBody() {

        for(WebElement elementoBody : lblGetBody){
            if(!elementoBody.getText().isEmpty()){
                String string =elementoBody.getText().replace("\n","");
                JSONObject json = new JSONObject(string);
                helpers.data.put("getLng",json.get("lng").toString());
                helpers.data.put("getLat",json.get("lat").toString());
                break;
            }
        }

    }

    public String validarCodeStatus() {
        withTimeoutOf(Duration.ofSeconds(15)).waitFor(lblCodeStatus);
        Serenity.takeScreenshot();
        return lblCodeStatus.getText();
    }
}
