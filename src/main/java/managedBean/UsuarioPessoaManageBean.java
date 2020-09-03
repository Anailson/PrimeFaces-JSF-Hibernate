package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import dao.DaoGeneric;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManageBean")
@ViewScoped
public class UsuarioPessoaManageBean {
	
	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<>();
	/*LIST PARA CARREGAR OS DADOS*/
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}
	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	
	public String salvar() {
		
		daoGeneric.salvar(usuarioPessoa);
		usuarioPessoa = new UsuarioPessoa(); //INSTANCIADO NOVO OBJETO ASSIM SALVAR E
		return "";//retorna na mesma tela ao salvar os objetos
	}
	
	public String novo() {
		
		usuarioPessoa = new UsuarioPessoa();//APOS SALVAR CRIAR UM NOVO OBJETO E A TELA FICA VAZIA
		return "";
	}

	public List<UsuarioPessoa> getList() {//APENAS O GET PRA PEGAR OS ATRIBUTOS
		list = daoGeneric.listar(UsuarioPessoa.class);
		return list;
	}
	public void setList(List<UsuarioPessoa> list) {
		this.list = list;
	}
	
	public String remover()	{
		
		daoGeneric.deletarPorId(usuarioPessoa);
		usuarioPessoa = new UsuarioPessoa(); //INSTANCIADO NOVO OBJETO
		return "";
		
	}	
}
