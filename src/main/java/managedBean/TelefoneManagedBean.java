package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoTelefones;
import dao.DaoUsuario;
import model.TelefoneUser;
import model.UsuarioPessoa;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

	private UsuarioPessoa user = new UsuarioPessoa();
    private DaoUsuario<UsuarioPessoa> daoUser = new DaoUsuario<UsuarioPessoa>();
	private DaoTelefones<TelefoneUser> daoTelefone = new DaoTelefones<TelefoneUser>();
	//OBJETO DO TELEFONE
	private TelefoneUser telefone = new TelefoneUser();
	

	@PostConstruct
	public void init() {

		String coduser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("codigouser");

		user = daoUser.pesquisar(Long.parseLong(coduser), UsuarioPessoa.class);

	}
	
	//METODO SALVAR
	public String salvar(){
		telefone.setUsuarioPessoa(user);//SABE DE QUMEM É O TELEFONE
		daoTelefone.salvar(telefone);
		telefone = new TelefoneUser();//APOS SALVAR UM TELEFONE CRIAR UM NOVO TELEFONE(OBJETO)
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class); //SALVA E RECARREGA O USUARIO
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação:",
				"Salvo com sucesso!"));
			
		return "";
	}
	
	//METODO REMOVER TELEFONE
	public String removeTelefone() throws Exception{
		
		daoTelefone.deletarPorId(telefone);
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);//APOS DELETA E RECARREGA O USUARIO com fones
		telefone = new TelefoneUser(); //INICIANDO NOVO OBJETO
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Telefone Removido!"));
		return "";
	}
	

	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}

	public UsuarioPessoa getUser() {
		return user;
	}
	
	
	public void setTelefone(TelefoneUser telefone) {
		this.telefone = telefone;
	}

	public TelefoneUser getTelefone() {
		return telefone;
	}
	
	public void setDaoTelefone(DaoTelefones<TelefoneUser> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}

	public DaoTelefones<TelefoneUser> getDaoTelefone() {
		return daoTelefone;
	}

	

}
