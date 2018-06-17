package chat;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClienteChat {
	
	static String mensagem;
	static String resposta;

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		try {
		//1. Conectar com o Servidor no IP 127.0.0.1 e porta 8879
		Socket servidor = new Socket("127.0.0.1", 8879);
		//Criar os canais (streams) de entrada e saída
		BufferedReader entrada = new BufferedReader(
				new InputStreamReader(servidor.getInputStream()));
		DataOutputStream saida = new DataOutputStream(servidor.getOutputStream());
		//2. Ler uma mensagem
		new Thread() {
			public void run()  {
				while (true) {
				//4. Recebendo uma mensagem (String) do servidor
					try {
						mensagem = entrada.readLine().toString();
						//TODO decriptacao aqui
						System.out.println(servidor.getInetAddress() + ">> " + mensagem + "\n");
					} catch (Exception e) {}
				}
			}
		}.start();
		
		new Thread() {
			public void run() {
				while (true) {
					//5. Enviando uma resposta
					try {
						System.out.print("Eu Cliente >> ");
						resposta = input.nextLine();
						//TODO encriptacao aqui
						saida.write((resposta + "\n").getBytes());
					} catch (Exception e) {}
				}
			}
		}.start();
		} catch(IOException e) { e.printStackTrace(); }
	}
}


