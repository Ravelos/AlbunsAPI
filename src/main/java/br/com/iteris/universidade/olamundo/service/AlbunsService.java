package br.com.iteris.universidade.olamundo.service;

import br.com.iteris.universidade.olamundo.domain.dto.AlbumCreateRequest;
import br.com.iteris.universidade.olamundo.domain.dto.AlbumUpdateRequest;
import br.com.iteris.universidade.olamundo.domain.dto.entity.Album;
import br.com.iteris.universidade.olamundo.exception.AlbumInvalidoException;
import br.com.iteris.universidade.olamundo.exception.AlbumNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class AlbunsService {
    private static List<Album> listaDeAlbuns; // fake, só para aprendizado
    private static int proximoId = 1;

    public AlbunsService() {
        if (listaDeAlbuns == null) {
            listaDeAlbuns = new ArrayList<>();
            listaDeAlbuns.add(new Album(proximoId++, "Da Lama ao Caos", "Chico Science & Nação Zumbi", 1994));
            listaDeAlbuns.add(new Album(proximoId++, "Fragile", "Yes", 1971));
            listaDeAlbuns.add(new Album(proximoId++, "This Is Acting", "Dia", 2016));
            listaDeAlbuns.add(new Album(proximoId++, "Clube da Esquina", "Milton Nascimento e Lô Borges", 1972));
        }
    }

    public Album criarAlbum(AlbumCreateRequest albumCreateRequest) {
        // Regra
        // Somente albums lançados entre 1950 e o ano atual.
        if (albumCreateRequest.getAnoLancamento() < 1950 || albumCreateRequest.getAnoLancamento() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new AlbumInvalidoException("Album não se encaixa nas regras de anos: maior que 1950 e menor que hoje");
        }

        //tudo certo, só cadastrar
        var novoAlbum = new Album(proximoId++, albumCreateRequest.getNome(), albumCreateRequest.getArtista(), albumCreateRequest.getAnoLancamento());
        listaDeAlbuns.add(novoAlbum);

        return novoAlbum;
    }
    public List<Album> listar() {
        return listaDeAlbuns;
    }

    public Album buscarPorId(Integer idAlbum) {
        var albumEncontrado = listaDeAlbuns.stream()
                .filter(album -> album.getIdAlbum() == idAlbum)
                .findFirst();

        if (albumEncontrado.isEmpty()) {
            throw new AlbumNaoEncontradoException();
        }

        return albumEncontrado.get();
    }

    public Album buscarPorNome(String nome) {
        var albumEncontrado = listaDeAlbuns.stream()
                .filter(album -> album.getNome().equals(nome))
                .findFirst();

        if (albumEncontrado.isEmpty()) {
            throw new AlbumNaoEncontradoException();
        }

        return albumEncontrado.get();
    }
    public Album atualizarAlbum(Integer idAlbum, AlbumUpdateRequest albumUpdateRequest) {
        var albumEncontrado = listaDeAlbuns.stream()
                .filter(album -> album.getIdAlbum() == idAlbum)
                .findFirst();

        if (albumEncontrado.isEmpty()) {
            throw new AlbumNaoEncontradoException();
        }

        var album = albumEncontrado.get();
        album.setArtista(albumUpdateRequest.getArtista());

        return albumEncontrado.get();
    }

    public Album deletarAlbum(Integer idAlbum) {
        var albumEncontrado = listaDeAlbuns.stream()
                .filter(album -> album.getIdAlbum() == idAlbum)
                .findFirst();

        if (albumEncontrado.isEmpty()) {
            throw new AlbumNaoEncontradoException();
        }

        var album = albumEncontrado.get();
        listaDeAlbuns.remove(album);

        return album;
    }
}
