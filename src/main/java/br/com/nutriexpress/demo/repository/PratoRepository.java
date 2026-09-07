package br.com.nutriexpress.demo.repository;

import br.com.nutriexpress.demo.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {
    
    // O Spring implementa a busca no banco magicamente só pelo nome do método!
    List<Prato> findByCategoria(String categoria); 

    boolean existsByNome(String nome);
}