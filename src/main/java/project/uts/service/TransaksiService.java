package project.uts.service;

import project.uts.entity.Transaksi;

import java.util.List;
import java.util.Optional;

public interface TransaksiService {

    List<Transaksi> getAllTransaksi();

    Optional<Transaksi> findById(Long id);

    Transaksi save(Transaksi std);

    void deleteById(Long id);
}
