package project.uts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.uts.entity.Transaksi;
import project.uts.exception.TransaksiNotFoundException;
import project.uts.service.TransaksiService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {
    private final TransaksiService transaksiService;

    @Autowired
    public TransaksiController(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
    }

    @GetMapping(value = "/{id}")
    public Transaksi getTransaksiById(@PathVariable("id") @Min(1) Long id) {
        Transaksi std = transaksiService.findById(id)
                .orElseThrow(() -> new TransaksiNotFoundException("Transaksi dengan ID " + id + " tidak ditemukan"));
        return std;
    }

    @PostMapping
    public Transaksi addTransaksi(@Valid @RequestBody Transaksi std) {
        return transaksiService.save(std);
    }

    @PutMapping(value = "/{id}")
    public Transaksi updateTransaksi(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody Transaksi newStd) {
        Transaksi transaksi = transaksiService.findById(id)
                .orElseThrow(() -> new TransaksiNotFoundException("Transaksi dengan ID " + id + " tidak ditemukan"));
        transaksi.setNamaBarang(newStd.getNamaBarang());
        transaksi.setJumlahBarang(newStd.getJumlahBarang());
        transaksi.setHargaBarang(newStd.getHargaBarang());
        return transaksiService.save(transaksi);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteTransaksi(@PathVariable("id") @Min(1) Long id) {
        Transaksi std = transaksiService.findById(id)
                .orElseThrow(() -> new TransaksiNotFoundException("Transaksi dengan ID " + id + " tidak ditemukan"));
        transaksiService.deleteById(std.getId());
        return "Transaksi dengan ID " + id + " sudah dihapus";
    }

}
