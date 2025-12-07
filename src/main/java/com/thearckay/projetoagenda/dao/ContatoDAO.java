package com.thearckay.projetoagenda.dao;

import com.thearckay.projetoagenda.enums.StatusContato;
import com.thearckay.projetoagenda.infra.FabricaConexao;
import com.thearckay.projetoagenda.model.Contato;
import com.thearckay.projetoagenda.model.Usuario;

import java.sql.*;
import java.time.LocalDate;

public class ContatoDAO {

    public StatusContato salvarContato(Contato contatoASalvar, Usuario usuario){
        try(Connection conexao = FabricaConexao.conectar()){
            String sqlInsertContato = "insert into contatos (usuario_id, nome_completo, numero_telefone, email) values (?,?,?,?);";

            PreparedStatement pstmt = conexao.prepareStatement(sqlInsertContato);

            pstmt.setInt(1, usuario.getId());
            pstmt.setString(2,contatoASalvar.getNomeCompleto());
            pstmt.setString(3, contatoASalvar.getNumeroTelefone());
            pstmt.setString(4, contatoASalvar.getEmail());

            pstmt.execute();
            return StatusContato.SUCESSO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StatusContato salvarContatoCompleto(Contato contatoASalvar){

        if (contatoASalvar.getNomeCompleto() == null || contatoASalvar.getNomeCompleto().isBlank()){
            return StatusContato.NOME_INVALIDO;
        }

        if (contatoASalvar.getNumeroTelefone() == null || contatoASalvar.getNumeroTelefone().isBlank()){
            return StatusContato.NUMERO_INVALIDO;
        }

        if (contatoASalvar.getEmail() == null || contatoASalvar.getEmail().isBlank()){
            return StatusContato.EMAIL_INVALIDO;
        }

        try(Connection conexao = FabricaConexao.conectar()){

            String sqlInsertContato = "insert into contatos(usuario_id,nome_completo, numero_telefone, email, localizacao, favorito, data_nascimento, deletado) values (?,?,?,?,?,?,?, ?);";

            PreparedStatement pstmt = conexao.prepareStatement(sqlInsertContato, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, contatoASalvar.getUsuarioId());
            pstmt.setString(2,contatoASalvar.getNomeCompleto());
            pstmt.setString(3, contatoASalvar.getNumeroTelefone());
            pstmt.setString(4, contatoASalvar.getEmail());
            pstmt.setString(5, contatoASalvar.getLocalizacao());
            pstmt.setBoolean(6, contatoASalvar.getFavorito());
            pstmt.setObject(7, contatoASalvar.getNascimento());
            pstmt.setBoolean(8, contatoASalvar.getDeletado());

            pstmt.execute();
            ResultSet idGerado = pstmt.getGeneratedKeys();

            if (idGerado.next()){
                contatoASalvar.setId(idGerado.getInt(1));
            }

            return StatusContato.SUCESSO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Contato pegarContatoPeloNumero(String numeroDoContato, Usuario usuario){
        try(Connection conexao = FabricaConexao.conectar()){
            String sqlSelectContato = "SELECT * FROM contatos WHERE numero_telefone = ? AND usuario_id = ?; ";

            PreparedStatement psmt = conexao.prepareStatement(sqlSelectContato);

            psmt.setString(1, numeroDoContato);
            psmt.setInt(2, usuario.getId());

            ResultSet rs = psmt.executeQuery();

            if (rs.next()){

                Contato contato = new Contato();
                contato.setId(rs.getInt("id"));
                contato.setUsuarioId(rs.getInt("usuario_id"));
                contato.setNomeCompleto(rs.getString("nome_completo"));
                contato.setNumeroTelefone(rs.getString("numero_telefone"));
                contato.setEmail(rs.getString("email"));
                contato.setLocalizacao(rs.getString("localizacao"));
                contato.setFavorito(rs.getBoolean("favorito"));
                contato.setNascimento(rs.getObject("data_nascimento", LocalDate.class));

                return contato;
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Contato pegarContatoPeloId(Integer idContato, Usuario usuarioDaAgenda){
        try(Connection conexao = FabricaConexao.conectar()){

            String sqlSelectContato = "select * from contatos where usuario_id = ? and id = ?;";
            PreparedStatement pstmt = conexao.prepareStatement(sqlSelectContato);

            pstmt.setInt(1, usuarioDaAgenda.getId());
            pstmt.setInt(2, idContato);
            ResultSet rs = pstmt.executeQuery();

            Contato contato = new Contato();
            if (rs.next()){
                contato.setId(rs.getInt("id"));
                contato.setUsuarioId(rs.getInt("usuario_id"));
                contato.setNomeCompleto(rs.getString("nome_completo"));
                contato.setNumeroTelefone(rs.getString("numero_telefone"));
                contato.setEmail(rs.getString("email"));
                contato.setLocalizacao(rs.getString("localizacao"));
                contato.setFavorito(rs.getBoolean("favorito"));
                contato.setNascimento(rs.getObject("data_nascimento", LocalDate.class));

                return contato;
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pegar o contato pelo id: "+e);
        }
    }

    public StatusContato atualizarContato(Contato contato, Integer idUsuarioLogado){

        if (contato.getNomeCompleto() == null || contato.getNomeCompleto().isBlank()){
            return StatusContato.NOME_INVALIDO;
        }

        if (contato.getNumeroTelefone() == null || contato.getNumeroTelefone().isBlank()){
            return StatusContato.NUMERO_INVALIDO;
        }

        if (contato.getEmail() == null || contato.getEmail().isBlank()){
            return StatusContato.EMAIL_INVALIDO;
        }

        try (Connection conexao = FabricaConexao.conectar()){
            String sqlUpdateContato = "update contatos set" +
                    " nome_completo = ?, numero_telefone = ?," +
                    " email = ?, localizacao = ?, favorito = ?, " +
                    "data_nascimento = ?, deletado = ? where id = ? and usuario_id = ?;";
            ;

            PreparedStatement pstmt = conexao.prepareStatement(sqlUpdateContato);
            pstmt.setString(1,contato.getNomeCompleto());
            pstmt.setString(2, contato.getNumeroTelefone());
            pstmt.setString(3, contato.getEmail());
            pstmt.setString(4, contato.getLocalizacao());
            pstmt.setBoolean(5, contato.getFavorito());
            pstmt.setObject(6, contato.getNascimento());
            pstmt.setBoolean(7, contato.getDeletado());

            pstmt.setInt(8, contato.getId());
            pstmt.setInt(9, idUsuarioLogado);

            System.out.println("Contato Salvo com sucesso!");

            pstmt.execute();

            return StatusContato.SUCESSO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o contato: "+e);
        }
    }

    public StatusContato deletarContato(Contato contato, Usuario usuarioLogado){
        if (contato.getId() == null){
            return StatusContato.ID_INVALIDO;
        }

        System.out.println("O id do contato é válido para deletar: "+contato.getId());
        System.out.println("Nome: "+contato.getNomeCompleto());
        System.out.println("Deletado: "+contato.getDeletado());

        try (Connection conexao = FabricaConexao.conectar()) {
            String sqlDeleteContato = "update contatos set deletado = true where id = ? and usuario_id= ?";

            PreparedStatement pstmt = conexao.prepareStatement(sqlDeleteContato);
            pstmt.setInt(1, contato.getId());
            pstmt.setInt(2, usuarioLogado.getId());

            pstmt.execute();
            return StatusContato.SUCESSO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar deletar o contato: "+e);
        }
    }


    public StatusContato deletarContatoDoBancoDeDados(Contato contato, Usuario usuarioLogado){
        if (contato.getId() == null){
            return StatusContato.ID_INVALIDO;
        }

        try (Connection conexao = FabricaConexao.conectar()) {
            String sqlDeleteContato = "delete from contatos where id = ? and usuario_id = ?;";

            PreparedStatement pstmt = conexao.prepareStatement(sqlDeleteContato);
            pstmt.setInt(1, contato.getId());
            pstmt.setInt(2, usuarioLogado.getId());

            pstmt.execute();
            return StatusContato.SUCESSO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar deletar o contato: "+e);
        }
    }

}
