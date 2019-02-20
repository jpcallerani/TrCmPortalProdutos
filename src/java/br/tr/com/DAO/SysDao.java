package br.tr.com.DAO;

import br.tr.com.Utils.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Administrador
 */
public class SysDao {

    private Session sessao;
    private boolean retorno;
    private List listagem;
    private String _erro = "";
    private int cod_usuario = 0;

    public SysDao() {
        this.sessao = (Session) NewHibernateUtil.getSessionFactory().openSession();
    }

    /**
     *
     * @param obj
     * @return
     */
    public String save(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.save(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            this._erro = e.getMessage();
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public Object saveReturnObj(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.save(obj);
            this.sessao.getTransaction().commit();
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            obj = null;
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
        return obj;
    }

    /**
     *
     * @param p_usuario
     * @return 
     */
    public Usuario executaFuncao(Usuario p_usuario) {
        Usuario v_usuario = null;
        try {
            this.sessao.beginTransaction();
            this.sessao.doWork((Connection cnctn) -> {
                CallableStatement call = cnctn.prepareCall("{ ? = call fnc_get_valida_usuario(?,?) }");
                call.registerOutParameter(1, Types.INTEGER); // or whatever it is
                call.setString(2, p_usuario.getEmail());
                call.setString(3, p_usuario.getSenha());
                call.execute();
                cod_usuario = call.getInt(1);
            });

            if (cod_usuario > 0) {
                List resultWithAliasedBean = this.sessao.createSQLQuery(
                        "select f.nome_func as login_func, "
                        + "       f.cod_func as cod_func, "
                        + "       f.nome as nome_func, "
                        + "       f.email as email, "
                        + "       f.ativo as ativo, "
                        + "       f.senha as senha, "
                        + "       nvl(n.nivel_acesso_nivel, 'CO') as nivel_acesso "
                        + "  from funcionarios f, tr_nivel_acesso n "
                        + " where f.cod_func = " + cod_usuario + " "
                        + "   and f.cod_func = n.nivel_acesso_id_func(+)")
                        .addScalar("login_func")
                        .addScalar("cod_func")
                        .addScalar("nome_func")
                        .addScalar("email")
                        .addScalar("ativo")
                        .addScalar("senha")
                        .addScalar("nivel_acesso")
                        .setResultTransformer(Transformers.aliasToBean(Usuario.class))
                        .list();

                v_usuario = (Usuario) resultWithAliasedBean.get(0);
            }

        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
        return v_usuario;
    }

    /**
     *
     * @param p_usuario
     * @return
     */
    public List listaFuncionarios() {
        List v_usuario = null;
        try {

            List resultWithAliasedBean = this.sessao.createSQLQuery(
                    "select f.nome_func as login_func, "
                    + "       f.cod_func as cod_func, "
                    + "       f.nome as nome_func, "
                    + "       f.email as email, "
                    + "       f.ativo as ativo, "
                    + "       f.senha as senha, "
                    + "       nvl(n.nivel_acesso_nivel, 'CO') as nivel_acesso "
                    + "  from funcionarios f, tr_nivel_acesso n "
                    + " where f.cod_func = n.nivel_acesso_id_func(+)"
                    + " and f.ativo = 'S'"
                    + " and f.SFW = 'S'"
                    + "order by nome_func")
                    .addScalar("login_func")
                    .addScalar("cod_func")
                    .addScalar("nome_func")
                    .addScalar("email")
                    .addScalar("ativo")
                    .addScalar("senha")
                    .addScalar("nivel_acesso")
                    .setResultTransformer(Transformers.aliasToBean(Usuario.class))
                    .list();

            v_usuario = resultWithAliasedBean;

        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
        return v_usuario;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String saveSemCommit(Object obj) {
        try {
            this.sessao.save(obj);
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            this._erro = e.getMessage();
            e.printStackTrace();
        }
        return this._erro;
    }

  
    /**
     *
     * @param obj
     * @return
     */
    public String update(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.update(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String delete(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.delete(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param clazz
     * @param p_list_argumentos
     * @param order
     * @param rownum 0 = Todas as linhas
     * @return
     */
    public List listagem(Class clazz, List<Criterion> p_list_argumentos, Order order, Integer rownum) {
        try {
            Criteria cri = sessao.createCriteria(clazz);
            p_list_argumentos.stream().forEach((criterion) -> {
                cri.add(criterion);
            });
            if (order != null) {
                cri.addOrder(order);
            }
            if (rownum > 0) {
                cri.setMaxResults(rownum);
            }
            cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            listagem = cri.list();
        } catch (Exception ex) {
            System.out.println(ex.getCause() + "------" + ex.getMessage());
            listagem = null;
        } finally {
            this.sessao.close();
        }
        return listagem;
    }

    /**
     *
     * @param obj
     * @param query
     * @param value
     * @param fieldName
     * @return
     */
    public List busca(Object obj, String query, int value, String fieldName) {
        try {
            Query qy = sessao.getNamedQuery(query);
            qy.setInteger(fieldName, value);
            listagem = qy.list();
        } catch (Exception ex) {
            listagem = null;
        } finally {
            this.sessao.close();
        }
        return listagem;
    }

    /**
     *
     */
    public void commit() {
        this.sessao.getTransaction().commit();
    }

    /**
     *
     */
    public void rollback() {
        this.sessao.getTransaction().rollback();
    }

    /**
     *
     */
    public void closeSession() {
        this.sessao.close();
    }

    /**
     *
     */
    public void openSession() {
        this.sessao.beginTransaction();
    }
}
