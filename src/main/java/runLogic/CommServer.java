package runLogic;


import actors.DatabaseManager;
import shared.OutgoingCommunicationRequest;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommServer {
    /* übernimmt die Verbindung nach außen, sowie das aktzeptieren von eingehenden verbindungen

     */
    //todo specify address + port lookup java rmi
    ServerSocket serverSocketIntern;
    public CommServer(){

    }
    public enum service  { REQUESTADDRESS}
    /*
    Folgendes Protokoll: Nach Aufbauen der Verbindung sendet der Client seinen Request als String an den Server, je nach Schlüsselwort wird ein anderer Service ausgeführt
    DepartmentList: Anfrage einer anderen Kommsteuerung, alle departments der Einrichtung werden übertragen
    Negotiate Recipant: Ein User möchte Daten an eine Institution übermitteln, die Kommsteuerung(User) schickt eine Anfrage Departmentlist an Kommsteuerung(Empfänger) und übermittelt diese zurück an den User
    Create CommRequest: Der Schritt Negotiate Recipant ist bereits erfolgt. Auf Userseite wird ein Objekt der Klasse OutgoingCommunicationRequest generiert und an die Kommsteuerung übertragen. Dieses wird an den CommManager weitergereicht
     */

    public void start(int portIntern){
        try{
            serverSocketIntern = new ServerSocket(portIntern);
        }catch(Exception e){
            e.printStackTrace();
        }

        while (true)
            try{
                new ClientHandler(serverSocketIntern.accept()).start();
            }catch(Exception e){
                e.printStackTrace();
            }

    }
    
    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private DatabaseManager manager = new DatabaseManager();

        boolean running;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {

            running = true;
            try{
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
            }catch (Exception e){
                e.printStackTrace();
            }

                try{
                    if(!actors.SecurityManager.authenticate()){
                        //todo security nötig für intern?
                    }
                    service request;
                    String requestType = in.readObject().toString();
                    if(requestType == null){//wenn die connection auf client seite geschlossen wurde, gibt read() null zurück
                        running = false;
                        close(in, out, clientSocket);

                    }

                    request = service.valueOf(requestType);
                    System.out.println("Service requested: " + requestType);
                    switch (request){
                        case REQUESTADDRESS:
                            String mID = readObject().toString();
                            writeObject(manager.getAddress(mID));
                            break;

                    }//switch(request)
                }catch (Exception e){
                    e.printStackTrace();
                }

            close(in, out, clientSocket);
            System.out.println("Server closed");
            }
            private Object readObject(){
                Object object;
                try{
                    object = in.readObject();
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                return object;
            }
            private boolean writeObject(Object o){
                try{
                    out.writeObject(o);
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
                return true;

            }

            private void close(ObjectInputStream in, ObjectOutputStream out, Socket clientSocket){
            try{
                in.close();
                out.close();
                clientSocket.close();

            }catch (Exception e){
                e.printStackTrace();
            }
            }


    }
}
