package chat;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.lang.String;

public class ServidorChat {

	static String mensagem;
	static String mensagemE;
	static String mensagemR;
	static String mensagemRi;
	String resposta;
	;
	static int n1, n2, n3, n4;

	static Scanner input = new Scanner(System.in);
	static  Cipher2 c2 = new Cipher2(); 

	public static void main(String[] args) throws Exception {

		//1. Escutando na porta 8879...
		ServerSocket servidor = new ServerSocket(8879);
		//2. Recebendo (aguardando) conexão de algum cliente
		Socket cliente = servidor.accept();
		//3. Iniciando canais (streams) de entrada e saída
		BufferedReader entrada = new BufferedReader(
				new InputStreamReader(cliente.getInputStream()));
		DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
		c2.initParameters();

		new Thread() {
			public void run()  {
				while (true) {
					//4. Recebendo uma mensagem (String) do cliente
					try {
						//System.out.println("Escreva mensagem: \n");
						mensagemR = entrada.readLine();
						//TODO decriptacao aqui
						c2.decrypt(mensagemR); // testar sem, ver se isto esta quebrando
						mensagemRi = c2.getTexto(); // modifiquei isto p ver se funciona 
						System.out.println("MENSAGEM PURA\n|>>| " + mensagemR + " |<<|");
						System.out.println("\n" + cliente.getInetAddress() + ">> " + mensagemRi + "\nRESP. AQUI\n>>");
					} catch (Exception e) {  }
				}
			}
		}.start();

		new Thread() {
			public void run() {
				while (true) {
					//5. Enviando uma resposta
					try {
						System.out.print("Eu Server \n>> ");
						//TODO encriptacao aqui
						//resposta = input.nextLine();
//						mensagem = input.nextLine(); // p o input do cliente
						if ((mensagem = input.nextLine()) != null) { // teste de mensagem vazia
							c2.encrypt(mensagem);
							System.out.println("Esta é a mensagem que não foi:\n|=>| " + mensagem + " |<=| \n");
						}
						mensagemE = c2.getTexto(); 
						//saida.write((resposta + "\n").getBytes());
						saida.write((mensagemE + "\n").getBytes());
					} catch (Exception e) {}
				}
			}
		}.start();
	}
}

