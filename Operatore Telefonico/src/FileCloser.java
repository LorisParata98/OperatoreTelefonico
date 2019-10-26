import java.io.Closeable;
import java.io.IOException;

public class FileCloser {
	public static void close(Closeable resource) {
		if(resource!=null) {
			try {
				resource.close();
			} catch (IOException e) {
				
				System.out.println(e.getStackTrace());
			}
		}
		
	}

}
