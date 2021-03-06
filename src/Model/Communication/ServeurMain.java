package Model.Communication;

import java.io.IOException;

public class ServeurMain {

        public static void main(String[] args) {

            if (args.length != 2) {
                usage();
            }
            int port = Integer.parseInt(args[0]);
            ServerTCP server = null;
            try {
                server = new ServerTCP(port,args[1]);
                server.mainloop();
            }
            catch(IOException e) {
                System.err.println("cannot communicate with client");
                e.printStackTrace();
                System.exit(1);
            }
        }

        public static void usage() {
        System.exit(1);
    }
}