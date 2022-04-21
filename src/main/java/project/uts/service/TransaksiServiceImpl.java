package project.uts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.uts.entity.Transaksi;
import project.uts.repository.TransaksiRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransaksiServiceImpl implements TransaksiService {
    private final TransaksiRepository transaksiRepository;

    @Autowired
    public TransaksiServiceImpl(TransaksiRepository studentRepository) {
        this.transaksiRepository = transaksiRepository;
    }

    @Override
    public List<Transaksi> getAllTransaksi() {

        return transaksiRepository.findAll();
    }

    @Override
    public Optional<Transaksi> findById(Long id) {
        return transaksiRepository.findById(id);
    }

    @Override
    public Transaksi save(Transaksi std) {
        return transaksiRepository.save(std);
    }

    @Override
    public void deleteById(Long id) {
        transaksiRepository.deleteById(id);
    }
}
