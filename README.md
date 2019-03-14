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

05/03/19
He creat la classe missatge, que té un camp més que la taula de base de dades per guardar el nom de l'autor del missatge. Quan es carrega un ingredient de la base de dades, es crida a la taula d'usuaris amb la fk que conté. 
També he creat la classe datasource i l'array adapter.
A la classe controller estic fent el métode per rebre els nous missatges del servidor. Encara no se si funciona. En qualsevol cas, tal com está ara la lógica, hauria d'enviar els misstges marcats amb pendent abans de rebre els missatges nous. Si finalment faig servir la taula pendents pels missatges sortints, podría fer aquests dos processos independents.

12/03
He fet la lógica per enviar els missatges sortint abans de recuperar els del servidor. 
Ara tenc problemes amb el token a l'hora de descarregar els missatges. 
Un pic arregli això, crec que només me quedará el métode per crear un missatge sortint.
Amb això cobriria lo bàsic.
Altres coses que voldria fer:
Afegir imatge de usuaris
Permetre filtrar els missatges per usuari
Separar la taula de missatges guardats i la bustia de sortida

14/03
He escrit el métode afegirmsgs de datasource
Reb el JSON del métode controller.carregamsgs a traves de mainactivity.mostramissatges
Carrega la listview pero se queda travada
Creada la lògica a datasource per introduir usuaris 
Ya carrega el nom d'usuari a listview 
vaig a fer la part d'UI d'enviar missatge
Ja envia missatges.
Després d'enviar es missatge carrega sa llista.
He fet que el teclat es mostri i s'oculti, i que l'edittext s'ajusti a la finestra i agafi el focus en mostrarse. 
Hi ha molt per optimitzar, però ja fa lo que ha de fer.




https://iesmantpc.000webhostapp.com/public/ajuda/