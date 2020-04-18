import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	private static final String COMMAND_DEPEND = "DEPEND";
	private static final String COMMAND_INSTALL = "INSTALL";
	private static final String COMMAND_REMOVE = "REMOVE";
	private static final String LIST = "LIST";

	public static void main(String[] args) throws IOException {
		Map<String, List<String>> map = new HashMap();
		Set<String> installedItems = new HashSet();
		Map parentReferences = new HashMap();
		
			Scanner myObj = new Scanner(System.in); 
			InputStreamReader input = new InputStreamReader(System.in) ;// Create a Scanner object
			BufferedReader reader = new BufferedReader(input);
			
			
				
		while (true) {
			
			String commandLineCommand = reader.readLine(); // Read user input

			String[] objs = commandLineCommand.split("\\s+");
			if (objs[0].contentEquals("DEPEND")) {
				List list = new ArrayList();
				for (int i = 2; i < objs.length; i++) {
					list.add(objs[i]);
					List list1 = new ArrayList();
					if (parentReferences.containsKey(objs[i])) {
						list1 = parentReferences.get(objs[i]) == null ? new ArrayList()
								: (ArrayList) parentReferences.get(objs[i]);

					}
					list1.add(objs[1]);
					parentReferences.put(objs[i], list1);
				}
				map.put(objs[1], list);
			} else if (objs[0].contentEquals("INSTALL")) {
				if (map.get(objs[1]) == null) {
					System.out.println("Installing " + objs[1]);
					installedItems.add(objs[1]);
				} else {
					List<String> dependencies = map.get(objs[1]);
					for (String s : dependencies) {
						if (!installedItems.contains(s)) {
							System.out.println("Installing " + s);
							installedItems.add(s);
						} else {
							System.out.println(s + "is already installed");
						}
					}

					if (!installedItems.contains(objs[1])) {
						System.out.println("Installing " + objs[1]);
						installedItems.add(objs[1]);
					}
				}

			} else if (objs[0].contentEquals("LIST")) {
				for (String s : installedItems) {
					System.out.println(s);
				}
			} else if (objs[0].contentEquals("REMOVE")) {
				if (installedItems.contains(objs[1])) {
					if (!parentReferences.containsKey(objs[1])) {
						List<String> str = map.get(objs[1]);
						for (String sf :str) {
							if (installedItems.contains(objs[1])) {
								System.out.println("Removing " +sf);
								installedItems.remove(objs[1]);
							}
						}
						
						System.out.println("Removing " +objs[1]);
						installedItems.remove(objs[1]);
					} else {

						List<String> childEmen = (List)parentReferences.get(objs[1]);
						
							for (String s2 : childEmen) {
								if (parentReferences.containsKey(s2) && installedItems.contains(s2)) {
									System.out.println(objs[1] + " is still needed");

								}

							}
						

					}
				}
			} else if (objs[0].contentEquals("END")) {
				System.exit(0);
			}
		}
	}

}