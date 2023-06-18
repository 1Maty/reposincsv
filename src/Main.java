import Entidades.Hashtag;
import Entidades.Piloto;
import Entidades.Tweet;
import Entidades.Usuario;
import uy.edu.um.prog2.adt.Hash.Exceptions.HashLleno;
import uy.edu.um.prog2.adt.Hash.MyHash;
import uy.edu.um.prog2.adt.Hash.MyHashImpl;
import uy.edu.um.prog2.adt.linkedlist.Exceptions.IlegalIndexException;
import uy.edu.um.prog2.adt.linkedlist.MyList;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    Scanner input = new Scanner(System.in);
    MyHash<String,Usuario> usuarios;
    MyList<String> nombresUsuarios;
    Tweet[] tweets ;
    Piloto[] pilotos;

   public Main(){
    try {
        CSVReaderReturn cositas = LeerCsv.leerTweets();
        this.pilotos = LeerCsv.leerPilotos();
        this.tweets = cositas.getTweets();
        this.usuarios= cositas.getUsuarios();
        this.nombresUsuarios= cositas.getNombresUsuarios();
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (HashLleno e) {
        throw new RuntimeException(e);
    }
       }
    public void topPilotosActivos() throws HashLleno {
        MyHash<String,Integer> contadorPilotos = new MyHashImpl<>(20);
        System.out.print("Ingresa el año:");
        String año= input.nextLine();
        System.out.print("Ingresa el mes:");
        String mes = input.nextLine();
        long startTime = System.nanoTime();
       for(int i =1; i< tweets.length;i++){
           if(tweets[i]==null) break;
           if(tweets[i].getFecha().split("-")[0].contains(año)&&(tweets[i].getFecha().split("-")[1].contains(mes))){
               String enMinuscula=tweets[i].getContent().toLowerCase();
               for(int j=0;j< pilotos.length;j++){
                    String nombrePiloto= pilotos[j].getNombre().split(" ")[0];
                    String apellidoPiloto= pilotos[j].getNombre().split(" ")[1];
                    if (pilotos[j].getNombre().split(" ").length>2) apellidoPiloto = apellidoPiloto + " " + pilotos[j].getNombre().split(" ")[2];//por el caso de "Nyck de vryes"
                   String apellidoPilotoMinuscula=apellidoPiloto.toLowerCase();
                   String nombrePilotoMinuscula=nombrePiloto.toLowerCase();
                    if(enMinuscula.contains(apellidoPilotoMinuscula)||enMinuscula.contains(nombrePilotoMinuscula)){
                        //pilotos[j].setContador(pilotos[j].getContador()+1);
                        if(contadorPilotos.get(pilotos[j].getNombre())==null) contadorPilotos.add(pilotos[j].getNombre(),1);
                        else{
                            contadorPilotos.get(pilotos[j].getNombre()).setValue(contadorPilotos.get(pilotos[j].getNombre()).getValue()+1);
                        }
                }}
           }
       }
       for(int i=0;i<pilotos.length;i++){
           pilotos[i].setContador(contadorPilotos.get(pilotos[i].getNombre()).getValue());
       }

        boolean cambiados;
        for(int i=0;i< pilotos.length-1;i++){
            cambiados=false;
            for (int j=0;j< pilotos.length-i-1;j++){
                int actual=pilotos[j].getContador();
                int siguiente=pilotos[j+1].getContador();
                if(actual<siguiente){
                    Piloto aux = pilotos[j];
                    pilotos[j] = pilotos[j+1];
                    pilotos[j+1]=aux;
                    cambiados=true;
                }
            }if(!cambiados)break;}
        for (int i=0;i<10;i++){
            System.out.println((i+1)+"Posicion----> "+pilotos[i].getNombre()+"  cantidad: "+pilotos[i].getContador());

        }long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();}
    public void cantidadHashtagsParaUnDia() throws IlegalIndexException, HashLleno {
        System.out.print("Ingresa el dia:");
        String dia=input.nextLine();
        long startTime = System.nanoTime();
        MyHash<String,Integer> listaHashtags = new MyHashImpl<>(10000);
        int cantidadHashtags=0;
        for(int i=1;i< tweets.length;i++){
            if(tweets[i]==null)break;
            if(tweets[i].getFecha().contains(dia)){
                String[] hashes = tweets[i].getHashtags().split("'");
                for(int j=0; j< hashes.length;j++){
                    if((j%2)!=0){
                        if(listaHashtags.get(hashes[j])==null){
                            listaHashtags.add(hashes[j],1);
                            cantidadHashtags++;
                    }
                    }
            }

        }}
        System.out.println("La cantidad de hashtags en el dia: "+cantidadHashtags);
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();}
    public void cantidadDeTweetsConPalabra(){
        System.out.print("Ingresa la palabra:");
        String palabra = input.nextLine();
        long startTime = System.nanoTime();
        int contador=0;
        String fraseMinuscula =palabra.toLowerCase();
        for(int i=1;i< tweets.length;i++){
            if(tweets[i]==null)break;
            if(tweets[i].getContent().contains(palabra)&&tweets[i].getContent().contains(fraseMinuscula)) contador++;
        }
        System.out.println("Cantidad de tweets con la palabra:" +contador);
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();}
    public void hashTagMasUsadoEnUnDia() throws IlegalIndexException, HashLleno {
       System.out.print("Ingresa el dia:");
       String dia=input.nextLine();
       long startTime = System.nanoTime();
       MyHash<String,Hashtag> listaDeHashtags = new MyHashImpl<>(10000);
       Hashtag masUsado = new Hashtag(0,"aux");
       for(int i=1;i< tweets.length;i++){
           if(tweets[i]==null)break;
           if(tweets[i].getFecha().contains(dia)){
               String hashes[] = tweets[i].getHashtags().split("'");
               for(int j=0;j<hashes.length;j++){
                   if((j%2)!=0){
                       if(hashes[j].contains("F1")||hashes[j].contains("f1")) continue;
                       Hashtag nuevoHashtag= new Hashtag(1,hashes[j]);
                       if(listaDeHashtags.get(hashes[j])==null){
                           listaDeHashtags.add(hashes[j],nuevoHashtag);
                           continue;}
                       listaDeHashtags.get(hashes[j]).getValue().setContador(listaDeHashtags.get(hashes[j]).getValue().getContador()+1);
                       if(listaDeHashtags.get(hashes[j]).getValue().getContador()>masUsado.getContador()) masUsado=listaDeHashtags.get(hashes[j]).getValue();
                   }
               }
           }
       }


        System.out.println("Hashtag mas usado:"+ masUsado.getText()+"  Cantidad de veces:"+masUsado.getContador());
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();
   }
   public void usuariosConMasTweets() throws HashLleno, IlegalIndexException {
       long startTime = System.nanoTime();

       int tamañoUsuarios = nombresUsuarios.size();
       Usuario[] usuariosAOrdenar = new Usuario[tamañoUsuarios];
       for(int i=0;i< nombresUsuarios.size();i++){
           usuariosAOrdenar[i]=usuarios.get(nombresUsuarios.get(i)).getValue();
       }
       MergeSort(usuariosAOrdenar);
       for(int i=0;i<15;i++){
           System.out.println(i+1+"--> "+usuariosAOrdenar[tamañoUsuarios-1-i].getNombreUsuario()+" Cantidad de Tweets: "+usuariosAOrdenar[tamañoUsuarios-1-i].getCantidadTweets()+" Verficado: "+usuariosAOrdenar[tamañoUsuarios-1-i].getVerificado());
       }
       long endTime = System.nanoTime();
       double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
       System.out.println();
       double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
       System.out.println("La función tardó " + roundedDuration + " segundos.");
       System.out.println();
   }



        public  static void  MergeSort(Usuario[] listaUsuarios){
       int largo=listaUsuarios.length;
       if(largo<=1) return;
       int medio=largo/2;
       Usuario[] arrayDeLaIzquierda = new Usuario[medio];
       Usuario[] arrayDeLaDerecha = new Usuario[largo-medio];

       int i=0;//array de la izquierda
             int j = 0; // array de la derecha
            for(;i<largo;i++){
                if(i<medio){
                    arrayDeLaIzquierda[i]=listaUsuarios[i];
                }
                else{arrayDeLaDerecha[j]=listaUsuarios[i];
                j++;}
            }
            MergeSort(arrayDeLaIzquierda);
            MergeSort(arrayDeLaDerecha);
            merge(arrayDeLaIzquierda,arrayDeLaDerecha,listaUsuarios);
        }
        private static void merge(Usuario[] arrayDeLaIzquierda,Usuario[] arrayDeLaDerecha,Usuario[] listaDeUsuarios){
            int tamañoIzquierda=listaDeUsuarios.length/2;
            int tamañoDerecha= listaDeUsuarios.length-tamañoIzquierda;
            int i=0;
            int l=0;
            int r=0;
            while(l<tamañoIzquierda && r<tamañoDerecha){
                if(arrayDeLaIzquierda[l].getCantidadTweets()<arrayDeLaDerecha[r].getCantidadTweets()){
                    listaDeUsuarios[i]=arrayDeLaIzquierda[l];
                    i++;
                    l++;
                }
                else{
                    listaDeUsuarios[i]=arrayDeLaDerecha[r];
                    i++;
                    r++;
                }
            }
            while(l<tamañoIzquierda){
                listaDeUsuarios[i] = arrayDeLaIzquierda[l];
                i++;
                l++;
            }
            while(r<tamañoDerecha){
                listaDeUsuarios[i] = arrayDeLaDerecha[r];
                i++;
                r++;
            }
        }



    public static void main(String [] args) throws IlegalIndexException, HashLleno {
    Scanner input = new Scanner(System.in);
    Main prueba =new Main();
    while (true){
    System.out.println("Ingrese una opción:");
    System.out.println("1. Listar los 10 pilotos más mencionados");
    System.out.println("2. Top 15 usuarios con más tweets");
    System.out.println("3. Cantidad de hashtags distintos para un día dado");
    System.out.println("4. Hashtag más usado para un día dado");
    System.out.println("5. Top 7 cuentas con más favoritos");
    System.out.println("6. Cantidad de tweets con una palabra o frase específica");
    System.out.println("0. Salir");
    System.out.print("Opcion:");
    int opcion = input.nextInt();
    if(opcion==0) break;
    if(opcion==1) prueba.topPilotosActivos();
    if(opcion==2) prueba.usuariosConMasTweets();
    if(opcion==6) prueba.cantidadDeTweetsConPalabra();
    if(opcion==3) prueba.cantidadHashtagsParaUnDia();
    if(opcion==4) prueba.hashTagMasUsadoEnUnDia();}
}}