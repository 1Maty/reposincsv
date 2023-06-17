import Entidades.Hashtag;
import Entidades.Piloto;
import Entidades.Tweet;
import Entidades.Usuario;
import uy.edu.um.prog2.adt.Hash.Exceptions.HashLleno;
import uy.edu.um.prog2.adt.Hash.MyHash;
import uy.edu.um.prog2.adt.Hash.MyHashImpl;
import uy.edu.um.prog2.adt.linkedlist.Exceptions.IlegalIndexException;
import uy.edu.um.prog2.adt.linkedlist.MyLinkedList;
import uy.edu.um.prog2.adt.linkedlist.MyList;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    Scanner input = new Scanner(System.in);
    Tweet[] tweets ;
    Piloto[] pilotos;

   public Main(){
    try{
        this.pilotos=LeerCsv.leerPilotos();
        this.tweets=LeerCsv.leerTweets();
    }catch (IOException e) {
        throw new RuntimeException(e);
    } //catch (IlegalIndexException e) {
        //throw new RuntimeException(e);
    }
   //}
    public void topPilotosActivos(){
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
                        pilotos[j].setContador(pilotos[j].getContador()+1);
                }}
           }
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
       MyList<String> nombreUsuarios = new MyLinkedList<>();
       MyHash<String,Usuario> usuarios = new MyHashImpl<>(150000);
       for(int i=1;i< tweets.length;i++){
           if(tweets[i]==null)break;
           if(usuarios.get(tweets[i].getUsuarioTweet().getNombreUsuario())==null){
               nombreUsuarios.add(tweets[i].getUsuarioTweet().getNombreUsuario());
               usuarios.add(tweets[i].getUsuarioTweet().getNombreUsuario(),tweets[i].getUsuarioTweet());
           }
           else{
               usuarios.get(tweets[i].getUsuarioTweet().getNombreUsuario()).getValue().setCantidadTweets(tweets[i].getUsuarioTweet().getCantidadTweets()+1);
           }
       }
       int tamañoUsuarios = nombreUsuarios.size();
       Usuario[] usuariosAOrdenar = new Usuario[tamañoUsuarios];
       for(int i=0;i<nombreUsuarios.size();i++){
           usuariosAOrdenar[i]=usuarios.get(nombreUsuarios.get(i)).getValue();
       }
       for(int i=0;i<usuariosAOrdenar.length;i++){
           Usuario[] listaChica = new Usuario[10000];
           listaChica[i]=usuariosAOrdenar[i];
           if(i==10000) break;
       }
       Usuario[] usariosOrdenados=Quicksort(usuariosAOrdenar);
       long endTime = System.nanoTime();
       double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
       System.out.println();
       double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
       System.out.println("La función tardó " + roundedDuration + " segundos.");
       System.out.println();
   }



        public  Usuario[]  Quicksort(Usuario[] listaUsuarios){
            quickSort(listaUsuarios, 0, listaUsuarios.length - 1);
   return listaUsuarios;
   }

        public static void quickSort(Usuario[] array, int low, int high) {
            if (low < high) {
                int pivotIndex = partition(array, low, high);
                quickSort(array, low, pivotIndex - 1);
                quickSort(array, pivotIndex + 1, high);
            }
        }

        public static int partition(Usuario[] array, int low, int high) {
            Usuario pivot = array[high];
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (array[j].getCantidadTweets() < pivot.getCantidadTweets()) {
                    i++;
                    swap(array, i, j);
                }
            }

            swap(array, i + 1, high);
            return i + 1;
        }

        public static void swap(Usuario[] array, int i, int j) {
            Usuario temp = array[i];
            array[i] = array[j];
            array[j] = temp;
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