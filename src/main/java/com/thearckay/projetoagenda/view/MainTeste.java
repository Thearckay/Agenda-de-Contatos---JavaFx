package com.thearckay.projetoagenda.view;

import com.thearckay.projetoagenda.dao.ContatoDAO;
import com.thearckay.projetoagenda.dao.NotaDAO;
import com.thearckay.projetoagenda.dao.UsuarioDAO;
import com.thearckay.projetoagenda.infra.BancoDeDadosSetup;
import com.thearckay.projetoagenda.model.Agenda;
import com.thearckay.projetoagenda.model.Contato;
import com.thearckay.projetoagenda.model.Nota;
import com.thearckay.projetoagenda.model.Usuario;

import java.time.LocalDate;

public class MainTeste {
    public static void main(String[] args) {
        System.out.println("Iniciando o banco de dados");
        BancoDeDadosSetup.inicializar();

        Agenda agenda = new Agenda();
        Usuario usuario = new Usuario("Kayck Arcanjo", "00 000000000", "BA", "kayck.arcanjo@gmail.com", "12345678", LocalDate.now(), agenda);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.salvarNovoUsuario(usuario);

        Usuario usuarioLogado = usuarioDAO.pegarUsuario("kayck.arcanjo@gmail.com", "12345678");
        System.out.println("Usuario logado ID: "+ usuarioLogado.getId());

        Contato contato = new Contato(usuarioLogado.getId(), "Amigo 1", "00 000000001", "amigo@gmail.com", "BA", true , LocalDate.now());
        ContatoDAO contatoDAO = new ContatoDAO();
        contatoDAO.salvarContatoCompleto(contato);
        System.out.println("contato salvo, id: "+contato.getId());

        Nota nota = new Nota(contato.getId(), "Aniversário dia 8");
        NotaDAO notaDAO = new NotaDAO();
        notaDAO.salvarNota(nota);

        System.out.println("----------- teste ------------");

        Contato contatoRecuperado = contatoDAO.pegarContatoPeloId(contato.getId(), usuarioLogado);
        contatoRecuperado.adicionarNota(nota);

        System.out.println("Nome: " + contatoRecuperado.getNomeCompleto());
        System.out.println("Notas encontradas: " + contatoRecuperado.getNotas().size());

        for (Nota n : contatoRecuperado.getNotas()) {
            System.out.println(" -> Nota: " + n.getTexto());
        }

    }
}
