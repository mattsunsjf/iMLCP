/**
 * 
 */
package imlcp.imlcp;

import java.util.Set;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @author mattsun
 *
 */
public class InterruptHandler implements SignalHandler {
	private SignalHandler prevHandler;
	final static private String signalName = "INT";
	
	/**
	 * 
	 */
	public InterruptHandler() {
		
	}

	public static void install() {
		Signal signal = new Signal(signalName);
		InterruptHandler handler = new InterruptHandler();
		handler.prevHandler = Signal.handle(signal, handler);
	}
	
	/**
	 * 
	 * @param signal
	 */
	public void handle(Signal signal) {
		try {
			System.out.println("Killing MLCP job");
			
			// Chain back to previous handler, if one exists
            if (prevHandler != SIG_DFL && prevHandler != SIG_IGN) {
                prevHandler.handle(signal);
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
