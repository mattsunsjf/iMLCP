/**
 * 
 */
package imlcp.imlcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

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
		public void execute(String[] cmd, InteractiveShell shell) throws Exception {
			shell.getConsole().clearScreen();
		}
	},
	EXIT {
		@Override
		public void execute(String[] cmd, InteractiveShell shell) throws Exception{};
	},
	/* MLCP Command */
	MLCP {
		@Override
		public void execute(String[] cmd, InteractiveShell shell) throws Exception {
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
		public void execute(String[] cmd, InteractiveShell shell) throws Exception {
			shell.logShellHelp();
		}
	},
	/* Enable/disable debug mode */
	DEBUG {
		@Override
		public void execute(String[] cmd, InteractiveShell shell) throws Exception {
			Level mlcpLevel = LogManager.getLogger("com.marklogic.contentpump").getLevel();
			Level connectorLevel = LogManager.getLogger("com.marklogic.mapreduce").getLevel();
			
			if (mlcpLevel == Level.DEBUG && connectorLevel == Level.DEBUG) {
				LogManager.getLogger("com.marklogic.contentpump").setLevel(Level.INFO);
				LogManager.getLogger("com.marklogic.mapreduce").setLevel(Level.INFO);
				System.out.println("MLCP debug mode off");
			} else {
				LogManager.getLogger("com.marklogic.contentpump").setLevel(Level.DEBUG);
				LogManager.getLogger("com.marklogic.mapreduce").setLevel(Level.DEBUG);
				System.out.println("MLCP debug mode on");
			}
			System.out.println("To change the startup configuration, please update log4j.properties");
		}
	},
	/* System Shell Command */
	SYS {
		@Override
		public void execute(String[] cmd, InteractiveShell shell) throws Exception {
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
	},
	/* MLCP Job History */
	JOBS {
		@Override
		public void execute(String[] cmd, InteractiveShell shell) throws Exception {
			List<String> cmdHistory = shell.getCmdHistory();
			List<String> jobHistory = shell.getJobHistory();
			
			if (cmd.length == 1) {
				for (int i = 0; i < cmdHistory.size(); i++) {
					System.out.println("[" + i + "] " + cmdHistory.get(i));
				}
				System.out.println();
				System.out.println("Use 'jobs [comma spearated number]' to view job histories. Example: jobs 1,2,4");
			} else {
				String[] idx = cmd[1].split(",");
				System.out.println();
				for (int i = 0; i < idx.length; i++) {
					int index = 0;
					
					try {
						index = Integer.parseInt(idx[i]);
					} catch (NumberFormatException e) {
						System.out.println("Invalid job number " + idx[i]);
						continue;
					}
					
					if (index > cmdHistory.size() || index > jobHistory.size()) {
						System.out.println("Invalid job number " + idx[i]);
						continue;
					}
					
					System.out.println("[" + i + "] " + cmdHistory.get(index));
					System.out.println(jobHistory.get(index));
				}
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
		} else if (cmd.equalsIgnoreCase("$")) {
			return SYS;
		} else if (isMlcpCommand(cmd)) {
			return MLCP;
		} else if (cmd.equalsIgnoreCase(JOBS.name())) {
			return JOBS;
		} else {
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
	
	public abstract void execute(String[] cmd, InteractiveShell shell) throws Exception;
	
	
}
