package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Objetos.Cliente;
import Objetos.Coche;

public class PrincipalS {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ServerSocket socket= new ServerSocket(7000);
		ArrayList<Cliente>clientes= new ArrayList<>();
		ArrayList<Coche>flotaCoches= new ArrayList<>();
			
		Cliente cliente1= new Cliente("12345678", "Soulfly", "Max", "Cavalera");
		Cliente cliente2= new Cliente("23412345", "Slipknot", "Corey", "Taylor");
		Cliente cliente3= new Cliente("56734562", "Ghost", "No me Acuerdo", "De su Nombre");
		clientes.add(cliente1);
		clientes.add(cliente2);
		clientes.add(cliente3);
		
		
		
		Coche coche1= new Coche("1234SMC", "Yundai", "i30", 205.0);
		Coche coche2= new Coche("4567SCT", "Ford", "Focus", 250.0);
		Coche coche3= new Coche("1479GND", "Seat", "Ibiza", 100.0);
		flotaCoches.add(coche1);
		flotaCoches.add(coche2);
		flotaCoches.add(coche3);
		
		Socket socketCliente= socket.accept();
		System.out.println("Cliente conectado");
			
		HablarCliente cliente= new HablarCliente(clientes, flotaCoches, socketCliente);
		
		cliente.start();
	}

}
