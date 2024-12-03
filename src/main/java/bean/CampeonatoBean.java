package bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.CampeonatoDao;
import entidades.Campeonato;

import java.util.List;

@ManagedBean
@ViewScoped
public class CampeonatoBean {

    private Campeonato campeonato = new Campeonato();
    private CampeonatoDao campeonatoDao = new CampeonatoDao();
    private List<Campeonato> campeonatos;

    @PostConstruct
    public void init() {
        carregarCampeonatos();
    }

    public void salvar() {
        try {
            campeonatoDao.salvar(campeonato);
            campeonato = new Campeonato(); 
            carregarCampeonatos(); 
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Campeonato salvo com sucesso!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar campeonato: " + e.getMessage(), null));
        }
    }

    private void carregarCampeonatos() {
        campeonatos = campeonatoDao.listarTodos();
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public List<Campeonato> getCampeonatos() {
        return campeonatos;
    }

    public void setCampeonatos(List<Campeonato> campeonatos) {
        this.campeonatos = campeonatos;
    }
}
