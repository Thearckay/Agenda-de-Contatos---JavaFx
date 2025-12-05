package com.thearckay.projetoagenda.dao;

import com.thearckay.projetoagenda.enums.StatusUsuario;
import com.thearckay.projetoagenda.infra.FabricaConexao;
import com.thearckay.projetoagenda.model.Agenda;
import com.thearckay.projetoagenda.model.Contato;
import com.thearckay.projetoagenda.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class UsuarioDAO {

    public StatusUsuario salvarNovoUsuario(Usuario usuario){

        if (usuario.getNomeCompleto() == null || usuario.getNomeCompleto().isBlank()){
            return StatusUsuario.NOME_VAZIO;
        }
        if (usuario.getNumeroTelefone() == null || usuario.getNumeroTelefone().isBlank()){
            return StatusUsuario.TELEFONE_VAZIO;
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()){
            return  StatusUsuario.EMAIL_VAZIO;
        }
        if (usuario.getLocalizacao() == null || usuario.getLocalizacao().isBlank()){
            return StatusUsuario.LOCALIZACAO_VAZIA;
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()){
            return StatusUsuario.SENHA_VAZIA;
        }
        if (usuario.getDataAniversario() == null){
            return StatusUsuario.DATA_NASCIMENTO_VAZIA;
        }

        try (Connection conexao = FabricaConexao.conectar()){

            String sqlInsertUsuario = "insert into usuarios" +
                    "(nome_completo, numero_telefone, localizacao, email, senha, data_aniversario )\n" +
            "values (?, ?, ?, ?, ?, ?);";

            PreparedStatement pstmt = conexao.prepareStatement(sqlInsertUsuario);
            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getNumeroTelefone());
            pstmt.setString(3, usuario.getLocalizacao());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getSenha());
            pstmt.setObject(6, usuario.getDataAniversario());

            pstmt.execute();

            return StatusUsuario.SUCESSO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao persistir o novo usuário no branco de dados! "+e);
        }
    }

    public StatusUsuario verificarDisponibilidadeEmail(String emailAVerificar){
        try (Connection conexao = FabricaConexao.conectar()){
            String sqlSelectEmailUsuario = "select email from usuarios where email = ?;";

            PreparedStatement pstmt = conexao.prepareStatement(sqlSelectEmailUsuario);
            pstmt.setString(1, emailAVerificar);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                return StatusUsuario.EMAIL_INDISPONIVEL;
            }

            return StatusUsuario.EMAIL_DISPONIVEL;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario pegarUsuario(String email, String senha){
        try(Connection conexao = FabricaConexao.conectar()){
            String sqlSelectUsuario = "select * from usuarios where email = ? and senha = ?;";

            PreparedStatement pstmt = conexao.prepareStatement(sqlSelectUsuario);
            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNomeCompleto(rs.getString("nome_completo"));
                usuario.setNumeroTelefone(rs.getString("numero_telefone"));
                usuario.setLocalizacao(rs.getString("localizacao"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setDataAniversario(rs.getObject("data_aniversario", LocalDate.class));

                Agenda agenda = new Agenda();
                agenda.setUsuarioDaAgenda(usuario);

                String slqSelectContatos = "select * from contatos where usuario_id = ?;";
                try (PreparedStatement pstmtAgenda = conexao.prepareStatement(slqSelectContatos);) {
                    pstmtAgenda.setInt(1,usuario.getId());

                    try (ResultSet rsAgenda = pstmtAgenda.executeQuery();) {
                        while (rsAgenda.next()){
                            Contato contato = new Contato();
                            contato.setId(rsAgenda.getInt("id"));
                            contato.setUsuarioId(rsAgenda.getInt("usuario_id"));
                            contato.setNomeCompleto(rsAgenda.getString("nome_completo"));
                            contato.setNumeroTelefone(rsAgenda.getString("numero_telefone"));
                            contato.setEmail(rsAgenda.getString("email"));
                            contato.setLocalizacao(rsAgenda.getString("localizacao"));
                            contato.setFavorito(rsAgenda.getBoolean("favorito"));

                            agenda.carregarContatoDoBanco(contato);

                        }
                    }
                }
                usuario.setAgenda(agenda);
                return usuario;
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao pegar/Logar o usuario: "+e);
        }
        return null;
    }

    public StatusUsuario atualizarUsuario(Usuario usuario){

        if (usuario.getNomeCompleto() == null || usuario.getNomeCompleto().isBlank()){
            return StatusUsuario.NOME_VAZIO;
        }
        if (usuario.getNumeroTelefone() == null || usuario.getNumeroTelefone().isBlank()){
            return StatusUsuario.TELEFONE_VAZIO;
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()){
            return  StatusUsuario.EMAIL_VAZIO;
        }
        if (usuario.getLocalizacao() == null || usuario.getLocalizacao().isBlank()){
            return StatusUsuario.LOCALIZACAO_VAZIA;
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()){
            return StatusUsuario.SENHA_VAZIA;
        }
        if (usuario.getDataAniversario() == null){
            return StatusUsuario.DATA_NASCIMENTO_VAZIA;
        }
        try(Connection conexao = FabricaConexao.conectar()){

            String sqlUpdateUsuario = "update usuarios " +
                    "set nome_completo = ?, numero_telefone = ?, " +
                    "localizacao = ?, email = ?, senha = ?, data_aniversario = ? " +
            "where id = ?;";

            PreparedStatement pstmt = conexao.prepareStatement(sqlUpdateUsuario);
            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getNumeroTelefone());
            pstmt.setString(3, usuario.getLocalizacao());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getSenha());
            pstmt.setObject(6, usuario.getDataAniversario());

            pstmt.setInt(7, usuario.getId());

            pstmt.execute();
            return StatusUsuario.SUCESSO;


        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar atualizar o usuário: "+e);
        }
    }

    public StatusUsuario deletarUsuario(Usuario usuario){
        if (usuario.getId() == null){
            return StatusUsuario.ID_INVALIDO;
        }

        try(Connection conexao = FabricaConexao.conectar()){

            String sqlDeleteUsuario = "delete from usuarios where id = ?";

            PreparedStatement pstmt = conexao.prepareStatement(sqlDeleteUsuario);
            pstmt.setInt(1, usuario.getId());
            pstmt.execute();

            return StatusUsuario.SUCESSO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar deletar o usuário"+e);
        }
    }
}
