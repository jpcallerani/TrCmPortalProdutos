create table tr_sessao (
id_sessao number not null,
nome_sessao varchar2(200) not null,
observacao varchar2(400),
valido varchar2(1)) tablespace TR_PORTAL_PRODUTOS_DATA;
--
alter table tr_sessao add constraint PK_ID_CLIENTE primary key (ID_SESSAO);
create table tr_classificacao (
id_classificacao number not null,
descricao varchar2(200) not null) tablespace TR_PORTAL_PRODUTOS_DATA;
--
alter table tr_classificacao add constraint PK_ID_CLASSIF primary key (ID_CLASSIFICACAO);
--
create table tr_arquivo (
id_arquivo number not null,
nome_arquivo varchar2(200) not null,
id_classificacao number,
id_sessao number,
data date not null) tablespace TR_PORTAL_PRODUTOS_DATA;
--
alter table tr_arquivo add constraint PK_ID_ARQUIVO primary key (ID_ARQUIVO);
--
alter table tr_arquivo add constraint FK_ARQUIVO_CLASSIF foreign key (ID_CLASSIFICACAO) references tr_classificacao (ID_CLASSIFICACAO);
--
alter table tr_arquivo add constraint FK_ARQUIVO_SESSAO foreign key (ID_SESSAO) references tr_sessao (ID_SESSAO);
--
create table tr_arquivo_download (id_arquivo_download number not null,
                                  arquivo varchar2(200) not null,
                                  content_type varchar2(50),
                                  id_arquivo number not null) tablespace TR_PORTAL_PRODUTOS_DATA;

alter table tr_arquivo_download add tamanho_arquivo number not null;
--
alter table tr_arquivo_download add nome_arquivo varchar2(200) not null;								  
                                  
alter table tr_arquivo_download add constraint PK_ID_ARQUIVO_DOWNLOAD primary key (id_arquivo_download);
--
alter table tr_arquivo_download add constraint FK_ARQUIVO_DOWNLOAD foreign key (id_arquivo) references tr_arquivo (id_arquivo) on delete cascade;
--
create table tr_calendario_versao (id_calendario number not null,
                                   nome varchar2(200) not null,
                                   data date not null,
                                   observacao varchar2(200)) tablespace TR_PORTAL_PRODUTOS_DATA;
--
alter table tr_calendario_versao add constraint PK_ID_CALENDARIO primary key (id_calendario);                                   
--

-- Create table
create table tr_nivel_acesso
(
  nivel_acesso_nivel   varchar2(3) not null,
  nivel_acesso_id_func number not null
)
tablespace TR_PORTAL_PRODUTOS_DATA;
  
-- create/recreate primary, unique and foreign key constraints 
alter table tr_nivel_acesso
  add primary key (nivel_acesso_nivel, nivel_acesso_id_func)
  using index 
  tablespace TR_PORTAL_PRODUTOS_DATA ;
--
create table tr_noticia (id_noticia number not null, titulo varchar2(200) not null, mensagem varchar2(3000) not null, data date not null);
--
alter table tr_noticia add constraint PK_ID_NOTICIA primary key (id_noticia);
--
create or replace function fnc_get_valida_usuario( p_usuario in varchar2, p_senha in varchar2 ) return number is
  v_cod_func number;
begin

  select cod_func
    into v_cod_func
    from funcionarios
   where nome_func = p_usuario
     and gf_decrypto(senha) = p_senha
     and ativo = 'S';

  return v_cod_func;

exception
  when no_data_found then
    return null;

end fnc_get_valida_usuario;

--
create or replace function gf_decrypto(p_s_Senha varchar2) return varchar2


-- Descrição: Função responsável por Descriptografar uma Senha.
-- @param p_s_Senha    Senha.
-- @return Retorna a senha descriptografada.


is
 
  TYPE tipo_Array is table of char index by binary_integer;
  s_Char          tipo_Array;
  s_SenhaDecrypto varchar2(256);
  s_SenhaCrypto   varchar2(256);
  Result          varchar2(256);
  s_Senha         char(256);
  n_Index         integer;
  n_Posicao       integer;
  n_IncrPos       integer;
  n_IncrChar      integer;
    
begin
  
  s_SenhaCrypto := substr(p_s_Senha,12,11) || substr(p_s_Senha,0,11);
  
  n_Index := 1;
  n_IncrPos := ascii(substr(s_SenhaCrypto,1,1)) - 33;
  s_Senha := substr(s_SenhaCrypto, 2, length(s_SenhaCrypto)-1);
  n_IncrChar := ascii(substr(s_Senha,1,1)) - 33;
  s_Senha := substr(s_Senha, 2, length(s_Senha) - 1);
  while (n_Index <= 10) loop
    n_Posicao := ascii(substr(s_Senha,1,1)) - 90 + n_IncrPos;
    s_Senha := substr(s_Senha, 2, length(s_Senha) - 1);
    s_Char(n_Posicao+1) := chr(ascii(substr(s_Senha,1,1)) - n_IncrChar);
    s_Senha := substr(s_Senha, 2, length(s_Senha) - 1);
    n_Index := n_Index + 1;
  end loop;
  n_Index := 1;
  s_SenhaDecrypto := '';
  while (n_Index <= 10) loop
    s_SenhaDecrypto := s_SenhaDecrypto || s_Char(n_Index);
    n_Index := n_Index + 1;
  end loop;
  s_SenhaDecrypto := ltrim(rtrim(s_SenhaDecrypto));
  Result := s_SenhaDecrypto;
  return(Result);
end;
/
--