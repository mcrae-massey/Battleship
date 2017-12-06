package client;

import java.io.PrintStream;

import common.MessageListener;
import common.MessageSource;

public class PrintStreamMessageListener implements MessageListener {

	/**
	 * Constructor
	 */
	public PrintStreamMessageListener(PrintStream out) {

	}
	
	/**
	 * 
	 * @param String message
	 * @param MessageSource source
	 */
	@Override
	public void messageReceived(String message, MessageSource source) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * 
	 * @param MessageSource source
	 */
	public void sourceClosed(MessageSource source) {
		// TODO Auto-generated method stub

	}

}
