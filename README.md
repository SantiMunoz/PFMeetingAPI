# PFMeetingAPI

## 1.Introducción
Plan&amp;Feedback Meeting Tool es una herramienta pensada para optimizar la gestión de una reunión, desde la fase de planificación hasta la redacción de la acta, pasando por el control del tiempo durante la reunión.

Este proyecto es accesible des del siguiente enlace: http://pfmeeting.com

A continuación se especificarán los elementos y rutas que componen la API REST del producto Plan&amp;Feedback Meeting Tool.


## 2. Elementos

Los elementos están en formato JSON.

Los atributos por defecto tienen el valor de null o 0 en el caso de atributos de tipo numérico.

Los atributos marcados con # no se pueden modificar por el cliente.


**Meeting**
  * #meetingId: *string*
  * title: *string*
  * date: *date* in milis
  * description: *string*
  * agenda: *array*
    * pointId: *string*
    * name: *string*
    * duration: *long* time in seconds
    * originalDuration: *long* time in seconds
    * comment: *string*
    * open: *boolean*
  * #init: *date* in milis
  * #pauseDate: *date* in milis
  * status: *string*
  * #initOffTime: *date* in milis
  * creatorEmail: *string*
  * calendarEventId: *string*
  * calendarId: *string*
  * #timeFinish: *date* in milis


**Token**
  * email: *string*
  * token: *string*
  * refreshToken: *string*
  
Valores de "status":
  * "play"
  * "pause"
  * "stop"
  * "orderChange"
  * "finsihed"
  * "offTime"
  
## 3. Meeting en formato JSON


```
{
  "meetingId" : "IzO3fpTHCeVh",
  "title" : "First monday of month Meeting",
  "date" : 1400486400000,
  "description" : "Plan all tasks of this month.",
  "agenda" : [ {
    "name" : "Expose tasks",
    "duration" : 1800,
    "originalDuration" : 1800,
    "comment" : "Sam: task1 and 2.\nMarc: task 3 and 4.\nJames: task 5 and 6. Help Marc with task 4",
    "pointId" : "dzoTXXOXs4xm",
    "open" : false
  }, {
    "name" : "Assign tasks",
    "duration" : 1800,
    "originalDuration" : 1800,
    "comment" : "",
    "pointId" : "fO6tlPex8hRR",
    "open" : false
  }, {
    "name" : "Re-assign",
    "duration" : 900,
    "originalDuration" : 900,
    "comment" : "",
    "pointId" : "46suDHqM8CvS",
    "open" : false
  } ],
  "init" : 0,
  "pauseDate" : 0,
  "status" : "stop",
  "initOffTime" : 0,
  "creatorEmail" : "YYY@gmail.com",
  "calendarEventId" : "awdawd51awd3c",
  "calendarId" : "XXXX@group.calendar.google.com",
  "timeFinish" : 0
}

```

## 4. Token en formato JSON
```
{
"email" : "YYY@gmail.com",
"token" : "awd54a5d43ad5awd5a4wd",
"refreshToken" : "awdawdaw445444w4w4ww4w4w4w4w"
}
```

## 5. Rutas de la API

URL base: http://pfmeeting.com/api

Todas las rutas que se especifican a continuación parten de la URL base.

### 5.1 Meeting

 Método | URL | Parámetros | Retorna | Descripción
 ------------- | ------------- | ------------- | ------------- | -------------
 **GET** | **/meetings/{meetingId}** | | Meeting | Obtenemos el meeting con el id especificado en la url.
 **PUT** | **/meetings** | Meeting | Meeting modificado | Llamada con doble funcionalidad: Si enviamos un meeting sin id la api le asigna un id y lo guarda en la base de datos. Finalmente retorna el meeting con el id asignado. Por otro lado, si enviamos un meeting con una id modifica el meeting ya existente en la base de datos.
 **PUT** | **/meetings/{meetingId}/status** | Status: *string* | | Modifica el atributo “status” del meeting con las operaciones correspondientes.
 **GET** | **/meetings/{meetingId}/acta** | | PDF | Genera la acta de la reunió en formato pdf.


### 5.2 Token

 Método | URL | Parámetros | Retorna | Descripción
 ------------- | ------------- | ------------- | ------------- | -------------
  **PUT** | **/token/{email}** | Authoritzation code: *string* | | Enviamos a la API el email del usuario y el Authoritzation code obtenido del proceso de autenticación. Posteriormente la api utiliza este código para obtener el Access token y el Refresh token del usuario.


## 5. Licencia

Copyright 2014 Santi Muñoz Mallofre Licensed under the Apache License, 
	Version 2.0 (the "License"); you may not use this file except in compliance 
	with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
	Unless required by applicable law or agreed to in writing, software distributed 
	under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES 
	OR CONDITIONS OF ANY KIND, either express or implied. See the License for 
	the specific language governing permissions and limitations under the License.
