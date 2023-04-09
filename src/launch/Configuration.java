package launch;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	public static String getValeur(String msg) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream in = new FileInputStream("Configuration.properties");
		prop.load(in);
		in.close();
				
		return prop.getProperty(msg);
		
	}

}
