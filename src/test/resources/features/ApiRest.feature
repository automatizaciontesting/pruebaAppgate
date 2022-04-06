# encoding: iso-8859-1
# Author: hervincamargo@gmail.com

Feature:Consumir API-REST
  Como usuario deseo consumir una API-REST desde una aplicacion
  Android con target API 19

@consumirApiRestAndroid
  Scenario: consumir API-REST con longitud y latitud definida por el usuario
  Given ingreso a la aplicacion android
  When ingreso la latitud y la longitud
  |hoja|
  |DatosApiRest|
  Then valido que los valores generados
