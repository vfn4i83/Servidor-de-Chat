package chat;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ServidorChat {

	static String mensagem;
	String resposta;
	Cipher2 c2;
	static int n1, n2, n3, n4;

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		//1. Escutando na porta 8879...
		ServerSocket servidor = new ServerSocket(8879);
		//2. Recebendo (aguardando) conexão de algum cliente
		Socket cliente = servidor.accept();
		//3. Iniciando canais (streams) de entrada e saída
		BufferedReader entrada = new BufferedReader(
				new InputStreamReader(cliente.getInputStream()));
		DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());

		//Criar um Scanner para digitar mensagem de resposta no servidor
		Cipher2 c2 = new Cipher2(); // modifiquei isto p ver se funciona
		System.out.println("Digite um numero");
		n1 = input.nextInt();
		System.out.println("Digite um numero");
		n2 = input.nextInt();
		System.out.println("Digite um numero");
		n3 = input.nextInt();
		System.out.println("Digite um numero");
		n4 = input.nextInt();
		//		c2.initParameters(n1, n2, n3, n4, true);

		new Thread() {
			public void run()  {
				while (true) {
					//4. Recebendo uma mensagem (String) do cliente
					try {
						//						System.out.println("Escreva mensagem: \n");
						mensagem = entrada.readLine().toString();
						c2.initParameters(n1, n2, n3, n4);
						c2.decrypt(mensagem).toString();
						//TODO decriptacao aqui / tentar getter setter DESCOBRIR PQ N RETORNA NADA
						mensagem = c2.getTexto().toString(); // modifiquei isto p ver se funciona 
						System.out.println(cliente.getInetAddress() + ">> " + mensagem + "\n");
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
						//mensagem = input.nextLine(); // p o input do cliente
						if ((mensagem = input.nextLine()) != null) { // teste de mensagem vazia
							c2.initParameters(n1, n2, n3, n4);
							c2.encrypt(mensagem);
						}
						mensagem = c2.getTexto(); 
						//saida.write((resposta + "\n").getBytes());
						saida.write((mensagem + "\n").getBytes());
					} catch (Exception e) {}
				}
			}
		}.start();
	}
}

