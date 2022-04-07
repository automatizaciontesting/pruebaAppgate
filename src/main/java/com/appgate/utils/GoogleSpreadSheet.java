package com.appgate.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.pages.PageObject;

import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GoogleSpreadSheet extends PageObject {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.DRIVE);
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    Logger logger = Logger.getLogger(GoogleSpreadSheet.class.getName());
    Helpers helpers = new Helpers();
    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    final String SPREADSHEETID = "1tXTLynvY31fXbYLe-nbX5HvhbPLTwxTxQAK3HLhMuik";

    public GoogleSpreadSheet() throws GeneralSecurityException, IOException {
    }

    private Credential getCredentials() throws IOException {
        LocalServerReceiver receiver = null;
        GoogleAuthorizationCodeFlow flow = null;
        try{
            InputStream accesoApiGoogle = new FileInputStream(obtenerCredencialesJson());
            if (accesoApiGoogle == null) {
                throw new FileNotFoundException("Ruta no encontrada en el directorio: " + obtenerCredencialesJson());
            }
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(accesoApiGoogle));
            // Build flow and trigger user authorization request.
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new File(String.valueOf(obtenerTokenApiGoogle()))))
                    .setAccessType("offline")
                    .build();
            receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        }catch (Exception e){
            logger.log(Level.INFO, e.getMessage());
        }
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void obtenerDataDrivenExcelOnline(int fila, DataTable dataExcel) {
        helpers.data.clear();
        obtenerDatosExcelOnLine(fila,dataExcel);
    }

    public void obtenerDatosExcelOnLine(int fila, DataTable dataExcel) {
        List<Map<String, String>> datosFeature = dataExcel.asMaps(String.class, String.class);
        try{

            String range = rangoDatadriven(datosFeature.get(0).get("Hoja"));
            Sheets service = new Sheets.Builder(httpTransport, JSON_FACTORY, getCredentials())
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEETID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values != null || !values.isEmpty()) {
                generaCabeceraExcel(values);
                for(int i = 0; i < values.size(); i++) {
                    if(i>0 && i==fila) {
                        helpers.data.put("fila",String.valueOf(fila+1));
                        /*Leemos los datos del excel online y de la fila correspondiente tomamos los valores del encabezado y fila*/
                        for (int j = 0; j < values.get(fila).size(); j++) {
                            helpers.data.put(values.get(0).get(j).toString(),values.get(fila).get(j).toString());
                        }
                        break;
                    }
                }
            }

        }catch (Exception e){
            logger.log(Level.INFO, e.getMessage());
        }

    }

    private void generaCabeceraExcel(List<List<Object>> valores) {
        int cantidadColumnas = valores.get(0).size();
        for(int i = 0; i < cantidadColumnas; i++) {
            helpers.data.put(valores.get(0).get(i).toString(),"");
        }
    }
    public File obtenerCredencialesJson(){
        File credenciales = new File("./src/test/resources/credential/credentials.json");
        return credenciales;
    }

    public File obtenerTokenApiGoogle(){
        File tokenApi = new File("./src/test/resources/credential/");
        return tokenApi;
    }
    public String rangoDatadriven(String hoja) {

        String rangoHoja;
        switch (hoja.trim()){
            case "DatosApiRest":
                    rangoHoja = hoja+"!A:D";
                break;

            default:
                assertThat("Actual hoja"+hoja, is(""));
                throw new IllegalStateException("Unexpected value: " + hoja.trim().toLowerCase());
        }
        return rangoHoja;
    }


}
