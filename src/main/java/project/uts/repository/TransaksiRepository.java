package project.uts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.uts.entity.Transaksi;

import java.util.List;
import java.util.Optional;

public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    List<Transaksi> findAll();
    Optional<Transaksi> findById(Long id);
    Transaksi save(Transaksi std);
    void deleteById(Long id);
}
