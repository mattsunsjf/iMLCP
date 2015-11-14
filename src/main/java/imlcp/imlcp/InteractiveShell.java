/**
 * 
 */
package imlcp.imlcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.util.VersionInfo;

import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

import com.marklogic.contentpump.ContentPump;

/**
 * @author mattsun
 *
 */
public class InteractiveShell {
	private ConsoleReader console;
	
	public InteractiveShell() throws Exception {
		console = new ConsoleReader();
	}
	
	protected void initialize() throws Exception {
		console.setPrompt("mlcp > ");
        console.setHandleUserInterrupt(true);
        ArgumentCompleter arguCompleter = new ArgumentCompleter(
        		new AggregateCompleter(
        		new StringsCompleter(configAutoCompletion()), 
        		new FileNameCompleter()));
        arguCompleter.setStrict(false);            
        console.addCompleter(arguCompleter);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		InteractiveShell iMlcp = new InteractiveShell();
		iMlcp.initialize();
		iMlcp.printHead();
		iMlcp.startConsole();
	}
	
	/* CommandLine Executor might be needed for Windows. Leave this here for now. */
	/*
	public void runShellCommand(String command){
		int iExitValue = 0;
		String sCommandString = command;
        CommandLine oCmdLine = CommandLine.parse(sCommandString);
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            iExitValue = oDefaultExecutor.execute(oCmdLine);
        } catch (ExecuteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
	
	protected void startConsole() throws Exception {
    	while (true) {
    		try {
    			String line = "";
    			String clipboardBuffer = "";
    	        while ((line = console.readLine()) != null) {
    	        	clipboardBuffer = line;
    	        	String[] arguments = preprocess(line);
    	        	Command command = null;
    	        	
    	        	try {
    	        		if (arguments == null || arguments.length < 1) {
    	        			continue;
    	        		}
    	        		command = Command.forName(arguments[0]);
    	        		
    	        		if (command != Command.MLCP && arguments.length > 1) {
    	        			throw new UnsupportedOperationException();
        	        	} else if (command == Command.EXIT) {
    	        			break;
    	        		} else {
    	        			command.execute(arguments, console);
    	        		}
    	        	} catch (UnsupportedOperationException e) {
    	        		logUnsupportedCommand(line);
    	        		continue;
    	        	}	            
    	        }
    		} catch (UserInterruptException e) {
    			// Catch CTRL+C
    			// TODO Try with mlcp job, whether it stops mlcp job
    			continue;
    		}
    	}
    }
	
	/* Command line string pre-process */
	
	protected String[] preprocess(String line) {
		line = line.trim();
		String[] args = null;
		if (line == "") {
			return args;
		} else if (line.startsWith("$")) {
			args = new String[2];
			args[0] = "$";
			args[1] = line.substring(1);
			return args;
		}
		args = split(line);
		unquote(args);
		unescape(args);
		return args;
	}
	
	// Split the command line by spaces, 
	// but ignore the tokens enclosed by double quotes or single quotes
	protected String[] split(String line) {
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\"|'.+?')\\s*").matcher(line);
		while (m.find()) {
			list.add(m.group(1));
		}
    	String[] args = new String[list.size()];
    	for (int i = 0; i< args.length; i++) {
    		args[i] = list.get(i);
    	}
    	
    	return args;
	}
	
	// Unescape escaped characters
	protected void unescape(String[] args) {
		char tab = '\t';
		String tabStr = "" + tab;
		char backslash = '\\';
		String backslashStr = "" + backslash;
		char newline = '\n';
		String newlineStr = "" + newline;
		
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].replace("\\t", tabStr);
			args[i] = args[i].replace("\\\\", backslashStr);
			args[i] = args[i].replace("\\n", newlineStr);
		}
	}	
	
	// Remove the double quotes or single quotes around the token
	protected void unquote(String[] args) {
		for (int i = 0; i< args.length; i++) {
			String trimmed = args[i].trim();
			if ((trimmed.startsWith("\"") && trimmed.endsWith("\"")) || 
					(trimmed.startsWith("'") && trimmed.endsWith("'"))) {
				args[i] = trimmed.substring(1, trimmed.length() - 2);
			}
		}
	}
	
	protected ArrayList<String> configAutoCompletion() throws Exception {
		ArrayList<String> dict = new ArrayList<String>();
		// Value String
		dict.add("aggregates");
		dict.add("archive");
		dict.add("binary");
		dict.add("byteswritable");
		dict.add("copy");
		dict.add("default");
		dict.add("delimited_text");
		dict.add("distributed");
		dict.add("documents");
		dict.add("export");
		dict.add("extract");
		dict.add("false");
		dict.add("forest");
		dict.add("full");
		dict.add("gzip");
		dict.add("help");
		dict.add("import");
		dict.add("json");
		dict.add("local");
		dict.add("localhost");
		dict.add("mixed");
		dict.add("none");
		dict.add("rdf");
		dict.add("sequencefile");
		dict.add("text");
		dict.add("true");
		dict.add("xml");
		dict.add("zip");
		// Option String
		dict.add("-aggregate_record_element");
		dict.add("-aggregate_record_namespace");
		dict.add("-aggregate_uri_id");
		dict.add("-archive_metadata_optional");
		dict.add("-batch_size");
		dict.add("-collection_filter");
		dict.add("-compress");
		dict.add("-conffilename");
		dict.add("-content_encoding");
		dict.add("-copy_collections");
		dict.add("-copy_permissions");
		dict.add("-copy_properties");
		dict.add("-copy_quality");
		dict.add("-database");
		dict.add("-delimited_root_name");
		dict.add("-delimited_uri_id");
		dict.add("-delimitercharacter");
		dict.add("-directory_filter");
		dict.add("-document_selector");
		dict.add("-document_type");
		dict.add("-fastload");
		dict.add("-filename_as_collection");
		dict.add("-generate_uri");
		dict.add("-hadoop_conf_dir");
		dict.add("-host");
		dict.add("-indented");
		dict.add("-input_compressed");
		dict.add("-input_compression_codec");
		dict.add("-input_database");
		dict.add("-input_file_path");
		dict.add("-input_file_pattern");
		dict.add("-input_file_typetype");
		dict.add("-input_host");
		dict.add("-input_password");
		dict.add("-input_port");
		dict.add("-input_username");
		dict.add("-max_split_size");
		dict.add("-min_split_size");
		dict.add("-mode");
		dict.add("-namespace");
		dict.add("-options_file");
		dict.add("-output_cleandir");
		dict.add("-output_collections");
		dict.add("-output_database");
		dict.add("-output_directory");
		dict.add("-output_file_path");
		dict.add("-output_host");
		dict.add("-output_language");
		dict.add("-output_partition");
		dict.add("-output_password");
		dict.add("-output_permissions");
		dict.add("-output_port");
		dict.add("-output_quality");
		dict.add("-output_type");
		dict.add("-output_uri_prefix");
		dict.add("-output_uri_replace");
		dict.add("-output_uri_suffix");
		dict.add("-output_username");
		dict.add("-password");
		dict.add("-path_namespace");
		dict.add("-port");
		dict.add("-sequencefile_key_class");
		dict.add("-sequencefile_value_class");
		dict.add("-sequencefile_value_type");
		dict.add("-snapshot");
		dict.add("-split_input");
		dict.add("-streaming");
		dict.add("-temporal_collection");
		dict.add("-thread_count");
		dict.add("-thread_count_per_split");
		dict.add("-tolerate_errors");
		dict.add("-transaction_size");
		dict.add("-transform_function");
		dict.add("-transform_module");
		dict.add("-transform_namespace");
		dict.add("-transform_param");
		dict.add("-type_filter");
		dict.add("-username");
		dict.add("-xml_repair_level");
		
		return dict;
	}
	
	/* From this line below, all the functions are used to print something */
	
	protected void printHead() throws IOException {
		System.out.println();
		logVersions();
		System.out.println("Type \"?\" for interactive shell help."
				+ " Type \"HELP\" for MLCP help.");
	}
	
	public static void logVersions() {
        System.out.println("ContentPump version: 1.3-3");
        System.out.println("Java version: " + 
            System.getProperty("java.version"));
        System.out.println("Hadoop version: " + VersionInfo.getVersion());
        System.out.println("Supported MarkLogic versions: " + 
                "6.0 - 8.0-3");
    }
	
	public static void logUnsupportedCommand(String cmd) {
		System.out.println("Unsupported command: " + cmd);
		System.out.println("Please refer to documentation or type ? to view supported command.");
	}
}
