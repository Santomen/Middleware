import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        int puertos[]={14001,14002,15001,15002};
       // Semaphore s2=new Semaphore(2);
        //Semaphore s1=new Semaphore(1);
       Transmisor conexion1=new Transmisor();
        Transmisor conexion2=new Transmisor();
        Transmisor conexion3=new Transmisor();
        Transmisor conexion4=new Transmisor();
        conexion1.establecer_puertos(14501,14001,puertos);
        conexion2.establecer_puertos(14502,14002,puertos);
        conexion3.establecer_puertos(14503,15001,puertos);
        conexion4.establecer_puertos(14504,15002,puertos);
        conexion1.start();
        conexion2.start();
        conexion3.start();
        conexion4.start();





    }
}