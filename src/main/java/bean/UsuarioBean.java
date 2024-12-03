package bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDao;
import entidades.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean {
   
    private Usuario usuario;
    private UsuarioDao usuarioDao = new UsuarioDao();
    private List<Usuario> usuarios;

    @PostConstruct
    public void init() {
        carregarUsuarios();
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }

    public void verificarLogin() {
        if (usuario == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void salvar() {
        usuarioDao.salvar(usuario);
        usuario = new Usuario(); 
        carregarUsuarios();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso!", null));
    }

    public String login() {
        Usuario usuarioAutenticado = usuarioDao.autenticar(usuario.getLogin(), usuario.getSenha());
        
        if (usuarioAutenticado != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioAutenticado);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário logado com sucesso!", null));
            
            return "opcoes.xhtml?faces-redirect=true"; 
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválidos", null));
            return null;
        }
    }

    public String sair() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "login.xhtml?faces-redirect=true"; 
    }

    public void carregarUsuarios() {
        usuarios = usuarioDao.listar();
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
