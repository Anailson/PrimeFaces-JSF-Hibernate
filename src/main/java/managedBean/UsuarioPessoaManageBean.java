package managedBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;

import com.google.gson.Gson;

import dao.DaoGeneric;
import dao.DaoUsuario;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManageBean")
@ViewScoped
public class UsuarioPessoaManageBean {
	
	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	//private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<>();
	/*LIST PARA CARREGAR OS DADOS*/
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	
	//DAO USUARIO REMOVENDO EM CASCATA
	private DaoUsuario<UsuarioPessoa> daoGeneric = new DaoUsuario<UsuarioPessoa>();
	
	@PostConstruct //CONSULTA O MEDOTO APENAS UMA VEZ
	public void init() {
		list = daoGeneric.listar(UsuarioPessoa.class);
	}
	
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}
	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	
	public String salvar() {
		
		daoGeneric.salvar(usuarioPessoa);
		list.add(usuarioPessoa); //SALVA E COLOCA NA LISTA O OBJETO
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação:","Salvo com Sucesso!"));
		usuarioPessoa = new UsuarioPessoa(); //INSTANCIADO NOVO OBJETO ASSIM SALVAR E
		return "";//retorna na mesma tela ao salvar os objetos
	}
	
	public String novo() {
		
		usuarioPessoa = new UsuarioPessoa();//APOS SALVAR CRIAR UM NOVO OBJETO E A TELA FICA VAZIA
		return "";
	}

	public List<UsuarioPessoa> getList() {//APENAS O GET PRA PEGAR OS ATRIBUTOS
	
		return list;
	}
	public void setList(List<UsuarioPessoa> list) {
		this.list = list;
	}
	
	public String remover()	{
		
		try {
			daoGeneric.removerUsuario(usuarioPessoa);//ENTRANDO NO MERCADO CRIANDO NA DaoUsuario que tem fone
			list.remove(usuarioPessoa); //REMOVENDO O OBJ DA LISTA
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Deletado:","Removido com sucesso!"));
			usuarioPessoa = new UsuarioPessoa(); //INSTANCIADO NOVO OBJETO
		} catch (Exception e) {
			
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação:","Existem telefones para o usuário!"));
			}else {
				e.printStackTrace();
			}
			
		}
		
		return "";
		
	}	
	
	//METODO DE PESQUISA CEP AJAX VIA CEP
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
			
			URL url = new URL("https://viacep.com.br/ws/"+usuarioPessoa.getCep()+"/json/");
			
			URLConnection connection = url.openConnection();
			InputStream is  = connection.getInputStream();
			BufferedReader br= new BufferedReader(new InputStreamReader(is,"UTF-8"));
		//VARRENDO A URL
		   String cep = "";
		   StringBuilder jsonCep = new StringBuilder();
		//LENDO LINHA A LINHA
		while ((cep = br.readLine()) != null) {
			jsonCep.append(cep);
		}
		  //  System.out.println(jsonCep.toString()); -> DEBUGANDO O OBJETO jsonCep e retornando os valores conforme o CEP
		//OBJETO AUXILIAR ->convertando os dados para dentro do objeto
		UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
	
		//System.out.println(userCepPessoa);
		//passand objeto dos dados do CEP
		usuarioPessoa.setCep(userCepPessoa.getCep());
		usuarioPessoa.setCep(userCepPessoa.getLogradouro());
		usuarioPessoa.setCep(userCepPessoa.getComplemento());
		usuarioPessoa.setCep(userCepPessoa.getBairro());
		usuarioPessoa.setCep(userCepPessoa.getLocalidade());
		usuarioPessoa.setCep(userCepPessoa.getUf());
		usuarioPessoa.setCep(userCepPessoa.getUnidade());
		usuarioPessoa.setCep(userCepPessoa.getIbge());
		usuarioPessoa.setCep(userCepPessoa.getGia());
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
