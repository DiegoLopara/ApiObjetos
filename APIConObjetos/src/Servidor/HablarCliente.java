package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Objetos.Cliente;
import Objetos.Coche;

public class HablarCliente extends Thread{

	private ArrayList<Cliente> clientes;
	private ArrayList<Coche> coches;
	private int correcto = 50;
	private int fallo = 200;
	private ObjectInputStream oI;
	private ObjectOutputStream oS;
	private DataOutputStream dO;
	private DataInputStream dI;

	private int elegir = 0;
	private int elegirClase = 0;
	private String recibirDNI;
	private boolean existeDNI = false;
	private Cliente meterCliente = null;
	private String recibirMatricula;
	private boolean existeMatricula = false;
	private Coche meterCoche = null;
	
	
	public HablarCliente(ArrayList<Cliente> a, ArrayList<Coche> b, Socket socket) throws IOException {

		this.clientes = a;
		this.coches = b;
	
		
		
		this.dO= new DataOutputStream(socket.getOutputStream());
		this.dI= new DataInputStream(socket.getInputStream());
		this.oI = new ObjectInputStream(socket.getInputStream());
		this.oS = new ObjectOutputStream(socket.getOutputStream());

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		

	
		
			
		do {
			
	
		
		do {
			try {
				
			
				
				elegir=this.dI.readInt();
				
				if (elegir > 0 && elegir < 4) {
					this.dO.writeInt(this.correcto);
				} else {
					this.dO.writeInt(this.fallo);
				}

			} catch (IOException e) {
				
			}
		} while (elegir < 1 || elegir > 3);

		if (elegir == 1) {
		
			
			try {
				do {
					elegirClase = this.dI.readInt();
					if (elegirClase == 1 || elegirClase == 2) {
						this.dO.writeInt(this.correcto);
					} else {
						this.dO.writeInt(this.fallo);
					}
				} while (elegirClase < 1 || elegirClase > 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (elegirClase == 1) {
				
				
				try {
					recibirDNI = this.dI.readUTF();

					for (Cliente cliente : clientes) {
						
						
						if (cliente.getDni().equals(recibirDNI)) {
							existeDNI = true;
							meterCliente = cliente;
							
						}

					}

					if (existeDNI == true) {
						
						this.dO.writeInt(this.correcto);
						this.oS.writeObject(meterCliente);
					} else {
						System.out.println();
						this.dO.writeInt(this.fallo);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (elegirClase == 2) {
				

				try {
					recibirMatricula = this.dI.readUTF();
					
					for (Coche coche : coches) {
						
						if (coche.getMatricula().equalsIgnoreCase(recibirMatricula)) {
							meterCoche = coche;
							existeMatricula = true;
						}
						
						

					}

					if (existeMatricula == true) {
						this.dO.writeInt(this.correcto);
						this.oS.writeObject(meterCoche);
					} else {
						this.dO.writeInt(this.fallo);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		else if (elegir == 2) {
			

			try {

				do {
					System.out.println(" esperando eleccion de cliente o coche");
					elegirClase = this.dI.readInt();
					if (elegirClase == 1 || elegirClase == 2) {
						this.dO.writeInt(this.correcto);
					} else {
						this.dO.writeInt(this.fallo);
					}
				} while (elegirClase < 1 || elegirClase > 2);
				
				if (elegirClase == 1) {
					

					Cliente clienteRecibido = (Cliente) this.oI.readObject();

					for (Cliente cliente : clientes) {
						if (cliente.getDni().equalsIgnoreCase(clienteRecibido.getDni())) {

							existeDNI = true;
						}

					}

					if (existeDNI == true) {
						this.dO.writeInt(this.fallo);
					} else {
						this.dO.writeInt(this.correcto);
						clientes.add(clienteRecibido);

					}
				} else if (elegirClase == 2) {
				

					Coche cocheRecibido = (Coche) this.oI.readObject();

					for (Coche coche : coches) {
						if (coche.getMatricula().equalsIgnoreCase(cocheRecibido.getMatricula())) {

						existeMatricula = true;
						}

					}

					if (existeMatricula == true) {
						this.dO.writeInt(this.fallo);
					} else {
						this.dO.writeInt(this.correcto);
						coches.add(cocheRecibido);

					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (elegir == 3) {
			
			
			try {
				this.dO.writeInt(clientes.size());
				this.dO.writeInt(coches.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		elegir = 0;
		elegirClase = 0;
		recibirDNI=null;
		existeDNI = false;
		meterCliente = null;
		recibirMatricula=null;
		existeMatricula = false;
		meterCoche = null;
		}while(true);
	}

	public int eleccionClase() throws IOException {
		
		int clase = 0;
		

		return correcto;
	}

}
