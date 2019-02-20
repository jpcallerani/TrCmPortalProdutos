package br.tr.com.Control;

import br.tr.com.DAO.SysDao;
import br.tr.com.Utils.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ControlUsuarioAdm {

    private List<Usuario> _usuario;
    private List<Criterion> _argumentos;
    private Order _order;
    private String _erro = "";

    public ControlUsuarioAdm() {
        this._argumentos = new ArrayList<>();
        _usuario = new ArrayList<>();
    }

    public List<Usuario> listaFuncionarios() {
        this._usuario = (List<Usuario>) new SysDao().listaFuncionarios();
        return _usuario;
    }
}
