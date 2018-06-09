package Model.Communication;

import java.io.IOException;

public class Client {
    public static void usage() {
        System.err.println("usage : java Client server_ip port");
        System.exit(1);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            usage();
        }
        int port = Integer.parseInt(args[1]);
        ClientTCP client = null;
        try {
            client = new ClientTCP(args[0], port, args[2]);
            client.initLoop();

        }
        catch(IOException | ClassNotFoundException e) {
            System.err.println("cannot communicate with server");
            e.printStackTrace();
            e.getMessage();
            System.exit(1);
        }
    }
}
