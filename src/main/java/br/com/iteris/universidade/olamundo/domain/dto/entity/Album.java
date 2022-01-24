package br.com.iteris.universidade.olamundo.domain.dto.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Album {
    private int idAlbum;
    private String nome;
    private String artista;
    private int anoLancamento;
}
