#missatgeria
Activitat final SPPMM

Repositori classe: https://github.com/iespaucasesnoves/missatgeria-pppaaauuu

22/02/19:
Començ a crear les classes. Faig les preferències tal com mos les passa en Tomeu. A QuepassaehHelper he creat una taula més per guardar els missatges pendents d'enviar i així evitar que col·lisionin amb els missatges entrants per la PK. Per fer això he creat l'string TABLE_PENDENTS i he copiat l'string de creació de la taula de la taula missatges i n'he canviat la part on dona nom a la taula, la resta será exactament igual. 
Me pos amb la mainactivity. Crec que el que hauria de fer, esquemàticament és:
1.- Carregar les preferències.
2.- Intentar fer login amb les dades guardades a les preferències (bucle while). 
 2.1.- Si no ho aconsegueix, cridar l'activitat login
3.- Si ho ha aconseguit, guarda nom, contraseña i token a preferencies. Mostrar la listview. La listview podria carregar els missatges de 50 en 50, perque quan n'hi hagi molts el rendiment no empitjori.
4.- Si hi ha missatges nous, mostrar un toast o una notificació.
5.- Davall la listview hi pot haver un edittext i un botó per enviar missatges. 

La listview hauria de tenir un onitemclicklistener que cridi a una activitat llegir missatge, que permeti visualitzar el missatge individualment. 
El layout del missatge a la llista hauria de mostrar nom de l'emisor i datahora

La lògica del mètode logIn l'he adaptat de https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp

M'he quedat fent el login a la classe controller. Contacta amb el servidor, però me diu que el login falla perquè no li pas els paràmetres.
He hagut d'utilitzar el mètode que ens ha passat en Tomeu per permetre connexions síncrones al main thread. 


26/02/19
Ja fa login. He fet un mètode específic pel login a la classe controller que potser se podría reconvertir per fer tots els POST.
El bucle del login l'estic fent a l'onactivityresult de la main activity en rebre la resposta de l'activitat Login.
Per tractar les respostes de servidor faig servir JSONobjects