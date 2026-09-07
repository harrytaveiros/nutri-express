package br.com.nutriexpress.demo.service;

import br.com.nutriexpress.demo.repository.PratoRepository;
import br.dtos.prato.PratoRequestDTO;
import br.dtos.prato.PratoResponseDTO;
import br.com.nutriexpress.demo.model.Prato;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;

    public PratoService(PratoRepository pratoRepository) {
        this.pratoRepository = pratoRepository;
    }

    // Simulação temporária do banco de dados
    private final List<Prato> bancoDeDadosSimulado = new ArrayList<>();
    private Long proximoId = 1L;

    public PratoResponseDTO criar(PratoRequestDTO dto) {
        // Verifica se já existe um prato cadastrado com este nome
        if (pratoRepository.existsByNome(dto.nome())) {
            throw new RuntimeException("Já existe um prato cadastrado com este nome");
        }

        Prato prato = toEntity(dto);
        
        prato.setId(proximoId++);
        bancoDeDadosSimulado.add(prato);
        
        return toDTO(prato);
    }

    public PratoResponseDTO buscarPorId(Long id) {
        Optional<Prato> pratoEncontrado = bancoDeDadosSimulado.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if(pratoEncontrado.isPresent()){
             return toDTO(pratoEncontrado.get());
        } else {
             throw new RuntimeException("Prato não encontrado com o ID: " + id);
        }
    }

    // Método para listar todos ou filtrar por categoria
    public List<PratoResponseDTO> listar(String categoria) {
        List<Prato> pratos;
        if (categoria != null) {
            pratos = pratoRepository.findByCategoria(categoria);
        } else {
            pratos = pratoRepository.findAll();
        }
        
        // Converte a lista de Entidades para uma lista de DTOs
        return pratos.stream()
                .map(this::toDTO)
                .toList(); // Se o Java reclamar do toList(), use .collect(Collectors.toList());
    }

    // Método para atualizar um prato existente
    public PratoResponseDTO atualizar(Long id, PratoRequestDTO dto) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado")); // Trateremos esse erro depois!

        prato.setNome(dto.nome());
        prato.setDescricao(dto.descricao());
        prato.setValor(dto.valor());
        prato.setCategoria(dto.categoria());
        prato.setCalorias(dto.calorias());
        prato.setQuantidade(dto.quantidade());
        prato.setUnidadeMedida(dto.unidadeMedida());

        Prato pratoAtualizado = pratoRepository.save(prato);
        return toDTO(pratoAtualizado);
    }

    // Método para deletar um prato
    public void deletar(Long id) {
        if (!pratoRepository.existsById(id)) {
            throw new RuntimeException("Prato não encontrado");
        }
        pratoRepository.deleteById(id);
    }

    private Prato toEntity(PratoRequestDTO dto) {
        Prato prato = new Prato();
        prato.setNome(dto.nome());
        prato.setDescricao(dto.descricao());
        prato.setValor(dto.valor());
        prato.setCategoria(dto.categoria());
        prato.setCalorias(dto.calorias());
        prato.setQuantidade(dto.quantidade());
        prato.setUnidadeMedida(dto.unidadeMedida());
        return prato;
    }

    private PratoResponseDTO toDTO(Prato prato) {
        return new PratoResponseDTO(
                prato.getId(),
                prato.getNome(),
                prato.getDescricao(),
                prato.getValor(),
                prato.getCategoria(),
                prato.getCalorias(),
                prato.getQuantidade(),
                prato.getUnidadeMedida()
        );
    }

    // --- DESAFIOS EXTRAS ---

    // 1. Filtro de calorias usando Stream (como exigido no material)
    public List<PratoResponseDTO> filtrarPorCalorias(Integer max) {
        List<Prato> todosOsPratos = pratoRepository.findAll();
        
        // Aqui usamos o Stream para filtrar a lista na memória
        return todosOsPratos.stream()
                .filter(p -> p.getCalorias() != null && p.getCalorias() <= max)
                .map(this::toDTO)
                .toList(); // Retorna a nova lista já convertida para DTO
    }

    // 2. Atualizar apenas um campo (PATCH)
    public PratoResponseDTO atualizarValor(Long id, BigDecimal novoValor) {
        // Aproveitamos a mesma lógica de erro que fizemos antes!
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado com o ID: " + id));
        
        // Alteramos apenas o valor e salvamos novamente
        prato.setValor(novoValor);
        Prato pratoAtualizado = pratoRepository.save(prato);
        
        return toDTO(pratoAtualizado);
    }
}