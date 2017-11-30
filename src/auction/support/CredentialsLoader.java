package auction.support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class CredentialsLoader {

	public static Map<String, String> getCredentials() {
		Map<String, String> users = new Hashtable<String, String>();
		try {
            FileReader reader = new FileReader("pwd.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
 	        
            String line; 
            while ((line = bufferedReader.readLine()) != null) {
            	String[] ps=line.split("::");
	        	users.put(ps[0], ps[1]);
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		return users;
	}
}