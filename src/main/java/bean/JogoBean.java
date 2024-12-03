package bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.CampeonatoDao;
import dao.JogoDao;
import entidades.Campeonato;
import entidades.Jogo;

import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class JogoBean {

    private Jogo jogo = new Jogo();
    private List<Jogo> jogos;
    private List<Campeonato> campeonatos;
  
    private JogoDao jogoDao = new JogoDao();
    private CampeonatoDao campeonatoDao = new CampeonatoDao();

    @PostConstruct
    public void init() {
        carregarCampeonatos();
        carregarJogos();
        if (jogo.getCampeonato() == null) {
            jogo.setCampeonato(new Campeonato());
        }
    }

    public void salvar() {
        try {
       
            if (jogo.getTime1().equals(jogo.getTime2())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Os times não podem ser iguais!", null));
                return;
            }

           
            Integer campeonatoId = jogo.getCampeonato().getId();
            if (campeonatoId == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um campeonato válido!", null));
                return;
            }

            Campeonato campeonatoSelecionado = campeonatoDao.buscarPorId(campeonatoId);
            if (campeonatoSelecionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campeonato não encontrado!", null));
                return;
            }

            jogo.setCampeonato(campeonatoSelecionado);
            jogo.setDataCadastro(new Date());
            jogoDao.salvar(jogo);

            carregarJogos(); 
            jogo = new Jogo();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Jogo salvo com sucesso!", null));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar o jogo: " + e.getMessage(), null));
        }
    }

    public void editar() {
        try {
            if (jogo == null || jogo.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Jogo não encontrado para edição!", null));
                return;
            }
            if (jogo.getTime1().equals(jogo.getTime2())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Os times não podem ser iguais!", null));
                return;
            }

            Integer campeonatoId = jogo.getCampeonato().getId();
            if (campeonatoId == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um campeonato válido!", null));
                return;
            }
            Campeonato campeonatoSelecionado = campeonatoDao.buscarPorId(campeonatoId);
            if (campeonatoSelecionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campeonato não encontrado!", null));
                return;
            }
            jogo.setCampeonato(campeonatoSelecionado);
            jogo.setDataCadastro(new Date());  
            jogoDao.editar(jogo);
            carregarJogos();
            jogo = new Jogo();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Jogo atualizado com sucesso!", null));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar o jogo: " + e.getMessage(), null));
        }
    }

    
    public void selecionarParaEdicao(Jogo jogo) {
        this.jogo = jogo;
    }
    
 

    public void excluir(Jogo jogo) {
        try {
            jogoDao.excluir(jogo.getId());
            jogos.remove(jogo);  
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Jogo excluído com sucesso!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir o jogo: " + e.getMessage(), null));
        }
    }
    

    public void carregarJogos() {
        jogos = jogoDao.listar();
        if (jogos == null || jogos.isEmpty()) {
            System.out.println("Nenhum jogo encontrado.");
        } else {
            System.out.println("Jogos carregados: " + jogos.size());
        }
    }

    public void carregarCampeonatos() {
        campeonatos = campeonatoDao.listarTodos();  
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public List<Campeonato> getCampeonatos() {
        return campeonatos;
    }

    public void setCampeonatos(List<Campeonato> campeonatos) {
        this.campeonatos = campeonatos;
    }
}