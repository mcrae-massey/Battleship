package server;

public class BattleShipDriver {

    public static void main(String[] args){
        if(args.length == 2){
            int size = Integer.parseInt(args[1]);
            if(size > 1){
                int port = Integer.parseInt(args[0]);
                BattleServer bs = new BattleServer(port,size);
                bs.listen();
                bs.shutDown();
            }
        }
        else{
            System.out.println("Invalid size, must be greater than 1");
        }        
        System.exit(0);
            

        }
    }

