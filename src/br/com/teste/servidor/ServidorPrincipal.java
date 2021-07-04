package br.com.teste.servidor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ServidorPrincipal {
	public static void main(String[] args) throws IOException, InterruptedException {
		ServerSocket servidor = new ServerSocket(30062);
		Socket socket;
		File arquivo;
		FileWriter fw;
		Scanner s;
		String mensagem;
		BufferedWriter bw;
		try {

			System.out.println("Iniciando Servidor...");

			while (true) {
				socket = servidor.accept();
				arquivo = new File("log.txt");
				fw = new FileWriter(arquivo, true);
				s = new Scanner(socket.getInputStream());
				mensagem = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - "
						+ s.nextLine();
				
				// Gravar arquivo
				bw = new BufferedWriter(fw);
				bw.write(mensagem);
				bw.newLine();
				bw.close();
				
				fw.close();

				System.out.println("Mensagem: '" + mensagem + "' armazenada");
				
				// Enviar resposta
				PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
				saidaCliente.println(mensagem);
				saidaCliente.close();
				s.close();
				socket.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
