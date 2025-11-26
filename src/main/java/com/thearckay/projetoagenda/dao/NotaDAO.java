package com.thearckay.projetoagenda.dao;

import com.thearckay.projetoagenda.infra.FabricaConexao;
import com.thearckay.projetoagenda.model.Nota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public void salvarNota(Nota nota) {
        try (Connection conexao = FabricaConexao.conectar()) {
            String sql = "INSERT INTO notas (contato_id, texto) VALUES (?, ?)";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, nota.getContatoId());
            pstmt.setString(2, nota.getTexto());

            pstmt.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar nota", e);
        }
    }

    public List<Nota> listarNotasPorContato(Integer contatoId) {
        List<Nota> notas = new ArrayList<>();
        try (Connection conexao = FabricaConexao.conectar()) {
            String sql = "SELECT * FROM notas WHERE contato_id = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, contatoId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Nota n = new Nota();
                n.setId(rs.getInt("id"));
                n.setContatoId(rs.getInt("contato_id"));
                n.setTexto(rs.getString("texto"));
                notas.add(n);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar notas", e);
        }
        return notas;
    }
}
