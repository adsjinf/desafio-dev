package spring.boot.desafiodev.cnab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.boot.desafiodev.cnab.model.Transacao;
import spring.boot.desafiodev.cnab.services.CnabService;

import java.util.List;

@RestController
@RequestMapping("/cnab")
public class CnabController {

    @Autowired
    private CnabService cnabService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        cnabService.arquivoProcessar(file);
        return ResponseEntity.ok("Arquivo processado com sucesso");
    }

    @GetMapping("/transacao")
    public List<Transacao> listarTransacoes(@RequestParam String param) {
        return cnabService.listarTransacoes();
    }

    @GetMapping("/transacaotipo")
    public List<Transacao> listarTransacoesPorTipo(@RequestParam String tipo) {
        return cnabService.listarTransacoesPorTipo(tipo);
    }

}
