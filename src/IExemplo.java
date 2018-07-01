import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IExemplo extends Remote
{
	void modifica(int n) throws RemoteException;
	int valor() throws RemoteException;
	Object listaArquivos() throws RemoteException;
	Object enviaArquivo(Integer id) throws RemoteException;
	public void addClientIP() throws RemoteException;
} 
