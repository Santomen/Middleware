

import com.example.calculadoracliente.Paquete;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
//ESTE ES EL TRANSMISOR QUE MANDA INFO AL BACK
public class Transmisor extends Thread{
    int middle_escucha;
    int puertos[];
    int p_emisor;
    public void establecer_puertos(int middle_escucha,int p_emisor,int puertos[]){
        this.middle_escucha=middle_escucha;
        this.puertos=puertos;
        this.p_emisor=p_emisor;
    }
    @Override // para usar polimorfism
    public void run(){
        //Server socket pondra a la app a la escucha de un puerto
        boolean ban=true;
        try{
            ServerSocket servidor=new ServerSocket(middle_escucha);
            //ahora que acepte cualquier conexion que venga del exterior con el metodo accept

            while(ban){
                Socket misocket=servidor.accept();//aceptara las conexiones que vengan del exterior
                ObjectInputStream flujo_entrada=new ObjectInputStream(misocket.getInputStream());
                Paquete mensaje=(Paquete)flujo_entrada.readObject();

                for(int puerto:puertos){
                    if(puerto!=p_emisor){
                        try {
                            Socket enviaReceptor=new Socket("127.0.0.1",(puerto));
                            System.out.println("Emisor "+p_emisor+" manda a "+puerto);
                            ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaReceptor.getOutputStream());
                            paqueteReenvio.writeObject(mensaje);
                            paqueteReenvio.close();
                            enviaReceptor.close();
                        } catch(IOException e) {
                            //System.out.println(e);
                           // System.out.println("servidor apagado: "+puerto);
                        }
                    }else{
                        System.out.println("De este puerto es mandado");
                    }


                }
                flujo_entrada.close();
                misocket.close();
            }

        }
        catch(IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
