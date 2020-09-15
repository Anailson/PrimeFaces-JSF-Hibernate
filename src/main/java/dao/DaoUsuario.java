package dao;

import model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa> {
	
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception{
		
		getEntityManager().getTransaction().begin();
		//SQL DO DELETE TELEFONE
		String sqlDelteFone = "delete from telefoneuser where usuariopessoa_id = " + pessoa.getId();
		getEntityManager().createNativeQuery(sqlDelteFone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}

}
