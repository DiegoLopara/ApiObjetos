package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Objetos.Cliente;
import Objetos.Coche;

public class Conexion extends Thread{

	private ObjectOutputStream oS;
	private ObjectInputStream oI;
	private DataOutputStream dO;
	private DataInputStream dI;
	private Socket socket;

	private static Scanner ln= new Scanner(System.in);
	private static Scanner sc= new Scanner (System.in);
	
	private int elegir=0;
	private int eleccionValida=0; // es valido cuando vale 50;
	private int claseVehiculo=0;
	private int eleccionValidaVehiculo=0;
	private int escribirDNI=0;
	private int escribirMatricula=0;
	private int enviarObjeto=0;
	
	public Conexion(Socket socket) throws IOException {
		super();

		this.socket = socket;
		this.dO= new DataOutputStream(socket.getOutputStream());
		this.dI= new DataInputStream(socket.getInputStream());
		this.oS = new ObjectOutputStream(socket.getOutputStream());
		this.oI = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		do {
			
			
			try {
		
			do {
				
				System.out.println("1- RECIBIR");
				System.out.println("2- ENVIAR");
				System.out.println("3- CANTIDAD DE DATOS");
				elegir = ln.nextInt();
				this.dO.writeInt(elegir);
				System.out.println("envia "+ elegir);
				eleccionValida = this.dI.readInt();
				if(eleccionValida==100) {
					System.out.println("Opcion no valida elige (1-2-3)");
				}
				
			} while (eleccionValida!=50);
			
			if (elegir==1){
				
				do {
					System.out.println("1- CLIENTE?");
					System.out.println("2- COCHE?");
					claseVehiculo= ln.nextInt();
					this.dO.writeInt(claseVehiculo);
					eleccionValidaVehiculo=this.dI.readInt();
					if(eleccionValidaVehiculo==300) {
						System.out.println("error en la eleccion elija (1-2)");
					}
				} while (eleccionValidaVehiculo!=50);
				
				if(claseVehiculo==1) {
					System.out.println("Introduce dni ");
					String dni=sc.nextLine();
					this.dO.writeUTF(dni);
					escribirDNI=this.dI.readInt();
					if(escribirDNI==100) {
						System.out.println("el dni no existe");
					}
					else {
						Cliente recibido= (Cliente) this.oI.readObject();	
					
						System.out.println("Nombre: "+recibido.getNombre()+", Apellidos : "+ recibido.getApellido1()+" "+ recibido.getApellido2());
					}
					
					
				}
				else if(claseVehiculo==2) {
					
					
					System.out.println("Introduce matricula");
					
					String matricula=sc.nextLine();
					this.dO.writeUTF(matricula);
					escribirMatricula=this.dI.readInt();
					if(escribirMatricula==300) {
						System.out.println("la matricula no existe");
					}
					else {
						Coche recepcionado= (Coche) this.oI.readObject();	
						
						System.out.println("Marca: "+recepcionado.getMarca()+" Modelo: "+recepcionado.getModelo()+" Potencia: "+ recepcionado.getPotencia());
					}
				}
				
				
			
				
			}
			else if(elegir==2) {
				
				do {
					System.out.println("1- CLIENTE");
					System.out.println("2- COCHE");
					claseVehiculo= ln.nextInt();
					this.dO.writeInt(claseVehiculo);
					eleccionValidaVehiculo=this.dI.readInt();
					if(eleccionValidaVehiculo==100) {
						System.out.println("error en la eleccion, elija(1-2)");
					}
				} while (eleccionValidaVehiculo!=50);
				
				if(claseVehiculo==1) {
				
					System.out.println("INTRODUCE DNI");
					String dni=sc.nextLine();
					System.out.println("INTRODUCE NOMBRE");
					String nombre=sc.nextLine();
					System.out.println("INTRODUCE APELLIDO1");
					String apellido1=sc.nextLine();
					System.out.println("INTRODUCE APELLIDO2");
					String apellido2=sc.nextLine();
					
					Cliente aEnviar= new Cliente(dni, nombre, apellido1, apellido2);
					this.oS.writeObject(aEnviar);
					enviarObjeto=this.dI.readInt();
					
					if(enviarObjeto==50) {
						System.out.println("Cliente añadido");
					}
					else if(enviarObjeto==100) {
						System.out.println("Ya existe el cliente");
					}
					
					
				}
				else if (claseVehiculo==2) {
					
					System.out.println("INTRODUCE MATRICULA");
					String matricula=sc.nextLine();
					System.out.println("INTRODUCE MARCA");
					String marca=sc.nextLine();
					System.out.println("INTRODUCE MODELO");
					String modelo=sc.nextLine();
					System.out.println("INTRODUCE POTENCIA");
					double potencia=ln.nextDouble();
					Coche paraEnviar= new Coche(matricula, marca, modelo, potencia);
					this.oS.writeObject(paraEnviar);
					
					enviarObjeto=this.dI.readInt();
					
					if(enviarObjeto==50) {
						System.out.println("Coche añadido");
					}
					else if(enviarObjeto==100) {
						System.out.println("Ya existe ese coche");
					}
					
				}
				
			}
			else if(elegir == 3) {
			
				int totalClientes=this.dI.readInt();
				int totalCoches=this.dI.readInt();		
				
				System.out.println("TOTAL "+ totalClientes+" CLIENTES");
				System.out.println("TOTAL "+totalCoches+" COCHES");
				
				
			}
			}catch (Exception e) {
				
			}
			
		
			elegir=0;
			eleccionValida=0; 
			claseVehiculo=0;
			eleccionValidaVehiculo=0;
			escribirDNI=0;
			escribirMatricula=0;
			enviarObjeto=0;	
			
		} while (true);
		
	
	
	
	}
}


