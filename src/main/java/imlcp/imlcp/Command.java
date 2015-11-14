/**
 * 
 */
package imlcp.imlcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.marklogic.contentpump.ContentPump;

import jline.console.ConsoleReader;

/**
 * 
 * Enum of supported commands.
 * @author mattsun
 *
 */
public enum Command {
	CLEAR {
		@Override
		public void execute(String[] cmd, ConsoleReader console) throws Exception {
			console.clearScreen();
		}
	},
	EXIT {
		@Override
		public void execute(String[] cmd, ConsoleReader console) throws Exception{};
	},
	/* MLCP Command */
	MLCP {
		@Override
		public void execute(String[] cmd, ConsoleReader console) throws Exception {
			try {
            	ContentPump.runCommand(cmd);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
		}
	},
	/* Help for iMLCP */
	USAGE {
		@Override
		public void execute(String[] cmd, ConsoleReader console) throws Exception {
			logShellHelp();
		}
	},
	/* Enable/disable debug mode */
	DEBUG {
		@Override
		public void execute(String[] cmd, ConsoleReader console) throws Exception {
			// TODO
		}
	},
	/* System Shell Command */
	SYS {
		@Override
		public void execute(String[] cmd, ConsoleReader console) throws Exception {
			Process p;
        	
        	String os = System.getProperty("os.name");
        	if (!os.toLowerCase().contains("windows")) {
        		p = Runtime.getRuntime().exec(
            			new String[] {"/bin/sh", "-c",
            					cmd[1]});
        	} else {
        		p = Runtime.getRuntime().exec(
            			new String[] {cmd[1]});
        	}
			p.waitFor();
             
			BufferedReader stdInput = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(
					new InputStreamReader(p.getErrorStream()));
	 
			String s = "";
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		}
	};
	public static Command forName(String cmd) throws IOException{
		if (cmd.equalsIgnoreCase(CLEAR.name())) {
			return CLEAR;
		} else if (cmd.equalsIgnoreCase(EXIT.name())) {
			return EXIT;
		} else if (cmd.equals("?")) {
			return USAGE;
		} else if (cmd.equalsIgnoreCase(DEBUG.name())) {
			return DEBUG;
		} else if (isMlcpCommand(cmd)) {
			return MLCP;
		} else if (cmd.equals("$")) {
			return SYS;
		}
		else {
			throw new UnsupportedOperationException();
		}
	}
	private static boolean isMlcpCommand(String cmd) {
		cmd = cmd.toLowerCase();
		if (cmd.equals("import") || cmd.equals("export") 
				|| cmd.equals("copy") ||cmd.equals("extract")
				|| cmd.equals("help")) {
			return true;
		} else {
			return false;
		}
	}
	
	public abstract void execute(String[] cmd, ConsoleReader console) throws Exception;
	
	protected void logShellHelp() throws IOException {
		System.out.println("MLCP Interactive Shell Help");
		System.out.println("----------------------------------------------------");
		System.out.println("[Command]              [Usage]");
		System.out.println("clear                  Clean the screen");
		System.out.println("exit                   Exit the MLCP interactive shell");
		System.out.println("$[command]             Execute system shell command. Example: $ls -al");
		System.out.println("help                   Help for MLCP");
		System.out.println("CTRL+C                 Discard current command line");
		System.out.println("?                      Help for interactive shell");
	}
}
