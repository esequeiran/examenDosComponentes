package com.cenfotec.workshop.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cenfotec.workshop.domain.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{
	/*public List<Categoria> findByNombreContaining(String word);*/
}
