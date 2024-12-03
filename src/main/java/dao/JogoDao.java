package dao;

import javax.persistence.*;

import entidades.Jogo;

import java.util.ArrayList;
import java.util.List;

public class JogoDao {
   
	 private EntityManagerFactory emf = Persistence.createEntityManagerFactory("integracaoPU");
	 
	 public void salvar(Jogo jogo) {
		 EntityManager em = emf.createEntityManager();
		 try {
			 em.getTransaction().begin();
			 em.persist(jogo);
			 em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			em.close();
		}
	 }
	 
	 public void editar(Jogo jogo) {
		 EntityManager em = emf.createEntityManager();
		 try {
			 em.getTransaction().begin();
			 em.merge(jogo);
			 em.getTransaction().commit();
		 }catch(Exception e){
			 em.getTransaction().rollback();
			 e.printStackTrace();
		 }finally {
			 em.close();
		 }
	 }
	 
	 public void excluir(Integer id) {
		 EntityManager em = emf.createEntityManager();
		 try {
			 em.getTransaction().begin();
			 Jogo jogo = em.find(Jogo.class, id);
			 if(jogo != null) {
				 em.remove(jogo);
			 }
			 em.getTransaction().commit();
		 }catch (Exception e) {
			 em.getTransaction().rollback();
			 e.printStackTrace();
		 }finally {
			 em.close();
		 }
	 }
	 
	 public List<Jogo> listar() {
		    try {
		        EntityManager em = emf.createEntityManager();
		        TypedQuery<Jogo> query = em.createQuery("SELECT j FROM Jogo j", Jogo.class);
		        return query.getResultList();
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ArrayList<>();
		    }
		}
}