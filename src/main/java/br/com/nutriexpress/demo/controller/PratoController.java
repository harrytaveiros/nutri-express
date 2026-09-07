package br.com.nutriexpress.demo.controller;

import br.dtos.prato.PratoRequestDTO;
import br.dtos.prato.PratoResponseDTO;
import br.com.nutriexpress.demo.service.PratoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    // Criar um novo prato (POST)
    @PostMapping
    public ResponseEntity<PratoResponseDTO> criar(@RequestBody @Valid PratoRequestDTO pratoRequestDTO) {
        PratoResponseDTO criado = pratoService.criar(pratoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // Buscar um prato pelo ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<PratoResponseDTO> buscarPorId(@PathVariable Long id) {
        PratoResponseDTO prato = pratoService.buscarPorId(id);
        return ResponseEntity.ok(prato);
    }

    // Listar todos os pratos ou filtrar por categoria (GET)
    @GetMapping
    public ResponseEntity<List<PratoResponseDTO>> listar(
            @RequestParam(required = false) String categoria) { // Filtro opcional via query param
        List<PratoResponseDTO> pratos = pratoService.listar(categoria);
        return ResponseEntity.ok(pratos);
    }

    // Atualizar todos os dados de um prato (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<PratoResponseDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody @Valid PratoRequestDTO pratoRequestDTO) { // Recebe e valida o corpo
        PratoResponseDTO atualizado = pratoService.atualizar(id, pratoRequestDTO);
        return ResponseEntity.ok(atualizado);
    }

    // Remover um prato (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pratoService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
    
    // --- ROTAS DOS DESAFIOS EXTRAS ---

    // 1. Rota para filtrar por máximo de calorias
    // A URL vai ficar: /pratos/calorias?max=500
    @GetMapping("/calorias")
    public ResponseEntity<List<PratoResponseDTO>> buscarPorCalorias(
            @RequestParam Integer max) { // Pega o valor "max" da URL
        
        List<PratoResponseDTO> filtrados = pratoService.filtrarPorCalorias(max);
        return ResponseEntity.ok(filtrados);
    }

    // 2. Rota para atualizar somente o valor usando PATCH
    // A URL vai ficar: /pratos/1/valor
    @PatchMapping("/{id}/valor")
    public ResponseEntity<PratoResponseDTO> atualizarValor(
            @PathVariable Long id, 
            @RequestBody Map<String, BigDecimal> request) { 
        
        // Extraímos apenas o campo "valor" do JSON enviado
        BigDecimal novoValor = request.get("valor");
        
        PratoResponseDTO atualizado = pratoService.atualizarValor(id, novoValor);
        return ResponseEntity.ok(atualizado);
    }
}