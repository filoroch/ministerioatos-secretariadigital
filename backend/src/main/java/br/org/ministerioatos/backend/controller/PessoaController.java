package br.org.ministerioatos.backend.controller;

import br.org.ministerioatos.backend.application.dto.RegisterCriancaInput;
import br.org.ministerioatos.backend.application.dto.RegisterPessoaInput;
import br.org.ministerioatos.backend.application.dto.RegisterVisistanteInput;
import br.org.ministerioatos.backend.application.usecase.RegisterCriancaUseCase;
import br.org.ministerioatos.backend.application.usecase.RegisterPessoaUseCase;
import br.org.ministerioatos.backend.application.usecase.RegisterVisistanteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/// TODO: fazer com que o frontend escolha qual endpoint chamar baseado no tipo de pessoa passada no formulário

@RestController("/api/pessoas")
public class PessoaController {


    private RegisterCriancaUseCase registerCrianca;
    private RegisterVisistanteUseCase registerVisitante;
//    private RegisterCongregadoUseCase registerCongregado;
//    private RegisterMembroUseCase registerMembro;

    public PessoaController(
            RegisterCriancaUseCase registerCrianca,
            RegisterVisistanteUseCase registerVisitante
//            RegisterCongregadoUseCase registerCongregado,
//            RegisterMembroUseCase registerMembro
    ) {
        this.registerCrianca = registerCrianca;
        this.registerVisitante = registerVisitante;
//        this.registerCongregado = registerCongregado;
//        this.registerMembro = registerMembro;
    }

    @PostMapping("/cadastro/crianca")
    public ResponseEntity registerCrianca(@RequestBody RegisterCriancaInput input){
        var output = registerCrianca.execute(input);
        return ResponseEntity
                .created(URI.create("pessoas/" + output.id()))
                .build();
    }

    @PostMapping("/cadastro/visitante")
    public ResponseEntity registerVisitante(@RequestBody RegisterVisistanteInput input){
        registerVisitante.execute(input);
    }
//
//    @PostMapping("/cadastro/congregado")
//    public ResponseEntity registerCongregado(@RequestBody RegisterCongregadoInput input){
//        registerCongregado.execute(input);
//    }
//
//    @PostMapping("/cadastro/membro")
//    public ResponseEntity registerCongregado(@RequestBody RegisterMembroInput input){
//        registerMembro.execute(input);
//    }
}
