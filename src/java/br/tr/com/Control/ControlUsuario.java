package br.tr.com.Control;

import br.tr.com.DAO.SysDao;
import br.tr.com.Utils.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ControlUsuario {

    private Usuario _usuario;
    private List<Criterion> _argumentos;
    private Order _order;
    private String _erro = "";

    public ControlUsuario() {
        this._argumentos = new ArrayList<>();
    }

    public Usuario validaUsuario(Usuario p_usuario) {
        _usuario = new SysDao().executaFuncao(p_usuario);
        return _usuario;
    }
}
