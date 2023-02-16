package Cliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PrincipalC {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		
		Conexion servidor = new Conexion(new Socket("127.0.0.1",7000));
		servidor.start();	

	}

}
