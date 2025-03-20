package spring.boot.desafiodev.cnab.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.boot.desafiodev.cnab.model.TipoTransacao;
import spring.boot.desafiodev.cnab.model.Transacao;
import spring.boot.desafiodev.cnab.repository.TransacaoRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CnabService {

    private TransacaoRepository transacaoRepository;

    public CnabService(TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
    }

    public void arquivoProcessar(MultipartFile file) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linha;
            while ((linha = br.readLine()) !=null) {
                Transacao transacao = parseLinha(linha);
                transacaoRepository.save(transacao);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Transacao parseLinha(String linha) {
        Transacao transacao = new Transacao();

        int tipoCodigo = Integer.parseInt(linha.substring(0, 1));
        TipoTransacao tipo = TipoTransacao.fromCodigo(tipoCodigo);

        transacao.setTipo(tipo.getDescricao());
        transacao.setNatureza(tipo.getNatureza());
        transacao.setSinal(tipo.getSinal());
        transacao.setData(LocalDate.parse(linha.substring(1, 9), DateTimeFormatter.BASIC_ISO_DATE));
        transacao.setValor(new BigDecimal(linha.substring(9, 19)).divide(new BigDecimal(100)));
        transacao.setCpf(linha.substring(19,30));
        transacao.setCartao(linha.substring(30, 42));
        transacao.setHora(LocalTime.parse(linha.substring(42, 48), DateTimeFormatter.ofPattern("HHmmss")));
        transacao.setDonoLoja(linha.substring(48, 62).trim());
        transacao.setNomeLoja(linha.substring(62, 80).trim());

        return transacao;
    }

    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }

    public List<Transacao> listarTransacoesPorTipo(String tipo) {
        return transacaoRepository.findByTipo(tipo);
    }
}
