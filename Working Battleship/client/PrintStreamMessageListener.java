package client;

import java.io.PrintStream;

import common.MessageListener;
import common.MessageSource;

public class PrintStreamMessageListener implements MessageListener{

    private PrintStream out;

    public PrintStreamMessageListener(PrintStream output){
        out = output;
    }

	@Override
	public void messageReceived(String message, MessageSource source) {
        out.println(message);		
		
	}

	@Override
	public void sourceClosed(MessageSource source) {
        out.close();		
	}

}
