package med.voll.api.domain.pacientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable pageable);

    @Query("SELECT p.ativo from Paciente p WHERE p.id = :id")
    Boolean findAtivoById(Long id);

}
