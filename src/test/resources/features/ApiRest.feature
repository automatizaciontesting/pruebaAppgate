# encoding: iso-8859-1
# Author: hervincamargo@gmail.com

Feature:Consumir API-REST
  Como usuario deseo consumir una API-REST desde una aplicacion
  Android con target API 19

@consumirApiRestAndroid
  Scenario Outline: consumir API-REST con longitud y latitud definida por el usuario
  Given que se ingresan los datos del servicio <Fila>
  |Hoja|
  |DatosApiRest|
  Then valido que los valores generados sean los correctos en el response
  Examples:
    | Fila |
    |    1 |