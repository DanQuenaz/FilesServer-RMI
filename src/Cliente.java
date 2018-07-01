import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.server.RemoteServer;
import java.io.*;
import java.util.*;

public class Cliente
{
	public static void main(String[] args) {
		int val, n;
		IExemplo server = null;
		Random rand = new Random();

		File receiveFolder = new File("../received/");
        if(!receiveFolder.isDirectory()){
            receiveFolder.mkdirs();
        }

		try {
			String endereco = "25.79.218.143";
			System.out.println("Localizando o objeto " + endereco);
			server = (IExemplo) Naming.lookup(endereco);			
			System.out.println("Objeto encontrado!");

			server.addClientIP();

			int op = 1;
			int op2 = 1;
			Map<Integer, File> files = new HashMap<Integer, File>();
			InputStreamReader streamTeclado = new InputStreamReader(System.in);
			BufferedReader tecladoStr = new BufferedReader(streamTeclado);
			Scanner tecladoInt = new Scanner(System.in); 
			
			while(op != 0){
				System.out.println("1. Listar arquivos do servidor.\n2. Listar arquivos locais.\n3. Listar clientes conectados.\n0. Sair");
				op = tecladoInt.nextInt();
				if(op == 1){
					Util.clearScreen();
					System.out.println("ARQUIVOS DO SERVIDOR\n\n");
					files = (Map<Integer, File>)server.listaArquivos();
					Integer count = 1;
					for (Integer index : files.keySet()){
							if(files.get(index).length() < 1000 ){
								System.out.println(index+" - "+files.get(index).getName() + " ------------ " + files.get(index).length()+"B");
							}
							else{
								System.out.println(index+" - "+files.get(index).getName() + " ------------ " + files.get(index).length()/1000+"KB");
							}
						count = count+1;
					}
					System.out.println("Escolha um arquivo ou digite 0 para voltar: ");
					op2 = tecladoInt.nextInt();
					if(op2 != 0){
						System.out.println("Recebendo arquivo... ");
						Mensagem aux = (Mensagem)server.enviaArquivo(op2);
						Util.gravaArquivo((byte[])aux.getData(), aux.getName());
						System.out.println("Arquivo recebido com sucesso!");
					}

				}else if(op == 2){
					Util.clearScreen();
					System.out.println("ARQUIVOS LOCAIS\n\n");
					Util.listaArquivos(new File("../received/"));
					
				}else if(op == 3){
					
				}
			}

		} catch (NotBoundException e) {
			System.err.println("Problema ao locarlizar o objeto remoto " + e);
			e.printStackTrace();
			System.exit(1);
		} catch (RemoteException e) {
			System.err.println("Falha durante a chamada do procedimento remoto! " + e);
			e.printStackTrace();
			System.exit(2);
		} catch (MalformedURLException e) {
			System.err.println("URL invalida!\n" + e);
			e.printStackTrace();
			System.exit(3);
		} 
 
	} 
} 
