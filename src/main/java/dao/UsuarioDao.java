package dao;

import javax.persistence.*;
import entidades.Usuario;
import java.util.List;

public class UsuarioDao {
  
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("integracaoPU");
    private EntityManager entityManager;
    

    public void salvar(Usuario usuario) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close(); 
        }
    }
    

    public List<Usuario> listar() {
        entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Usuario", Usuario.class).getResultList();
        } finally {
            entityManager.close(); 
        }
    }
    
  
    public Usuario autenticar(String login, String senha) {
        entityManager = emf.createEntityManager(); 
        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("login", login);
            query.setParameter("senha", senha);

            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } finally {
            entityManager.close(); 
        }
    }
}
