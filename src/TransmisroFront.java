
import com.example.calculadoracliente.Paquete;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class TransmisroFront extends Thread{
    int middle_escucha;
    int middle_envia;
    int middle_envia2;
    Semaphore s1;
    Semaphore s2;
    public void semaforos(Semaphore s1,Semaphore s2){
        this.s1=s1;
        this.s2=s2;
    }

    public void puertos(int middle_escucha,int middle_envia){
        this.middle_escucha=middle_escucha;
        this.middle_envia=middle_envia;
        this.middle_envia2=middle_envia;
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
                mensaje.setCadena(mensaje.getCadena()+String.valueOf(middle_escucha));
                try {
                    s2.acquire();
                    s1.acquire();
                    for(int i=0;i<2;i++){
                        Socket enviaReceptor=new Socket("127.0.0.1",(middle_envia+i));
                        ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaReceptor.getOutputStream());
                        paqueteReenvio.writeObject(mensaje);
                        paqueteReenvio.close();
                        enviaReceptor.close();

                    }

                    try {
                        //Ponemos a "Dormir" el programa durante los ms que queremos
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e);
                    }


                    s1.release();
                    s2.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                flujo_entrada.close();
                misocket.close();
            }

        }
        catch(IOException e){

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
