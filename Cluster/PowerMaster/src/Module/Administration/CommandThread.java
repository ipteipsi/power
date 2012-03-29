/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.Administration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KopDicht
 */
public class CommandThread extends Thread {

    private ServerSocket server1;
    
    public CommandThread() throws IOException{
        server1 = new ServerSocket(666, 10);        
    }
    
    @Override
    public void run() {
        //ServerSocket server2 = new ServerSocket(667, 10);
        while (true) {
            try {
                Socket socket1 = server1.accept();
                //Socket socket2 = server2.accept();

                AdministrationClient receber1 = new AdministrationClient(socket1);
                //Thread_recebe receber2 = new Thread_recebe(socket2);

                receber1.start();
                //receber2.start();
            } catch (IOException ex) {
                Logger.getLogger(CommandThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
