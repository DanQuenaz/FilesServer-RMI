import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.RemoteServer;
import java.io.*;
import java.util.*;

public class Servidor extends UnicastRemoteObject implements IExemplo {
	private int contador;
	private Map<Integer, File> files;
	private Vector<String> clients;

	public Servidor() throws RemoteException { 
		super();
		files = new HashMap<Integer, File>();
		clients = new Vector<String>();
		contador = 0;
	} 

	public void modifica(int n) {
		contador += n;
		System.out.println("Valor da alteração: " + n );
		
	}

	public Object listaArquivos(){
		Util.listaArquivos(new File("../serverFiles/"), this.files);
		return files;
	}

	public Object enviaArquivo(Integer id){
		Mensagem aux = new Mensagem(this.files.get(id), Util.capturaArquivo(this.files.get(id)));
		return aux;
	}

	public Object enviaArquivo(File flw){
		return Util.capturaArquivo(flw);
	}

	public String addClientIP(){
		try{
			this.clients.add(RemoteServer.getClientHost());
			System.out.println("Cliente: "+RemoteServer.getClientHost()+" conectado!");
		}catch(Exception e){
			return null;
		}
	}

	public int valor() {
		return contador;
	} 

	public static void main(String[] args) {
		try {
			System.setProperty("java.rmi.server.hostname", "192.168.2.26");
			Servidor server = new Servidor();
			String endereco = "192.168.2.26";
			System.out.println("Registering " + endereco + "...");
			Naming.rebind(endereco, server);
			System.out.println("Registrado!");
		} catch (RemoteException e) {
			System.err.println("Erro durante o registro do objeto! " + e);
			e.printStackTrace();
			System.exit(1); 
		} catch (MalformedURLException e) {
			System.err.println("URL invalida! " + e);
			e.printStackTrace();
			System.exit(2);
		} 

	} 
}
