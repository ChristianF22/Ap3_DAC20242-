package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Campeonato;
import util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class CampeonatoDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("integracaoPU");

    public void salvar(Campeonato campeonato) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (campeonato.getId() == null) {
                em.persist(campeonato); 
            } else {
                em.merge(campeonato);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void excluir(Campeonato campeonato) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Campeonato c = em.find(Campeonato.class, campeonato.getId());
            if (c != null) {
                em.remove(c);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Campeonato buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Campeonato.class, id);
        } finally {
            em.close();
        }
    }

    public List<Campeonato> listarTodos() {
         EntityManager em = JPAUtil.criarEntityManager();
        try {
            return em.createQuery("SELECT c FROM Campeonato c", Campeonato.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
