package project.uts.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "transaksi")
@Data

public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nama Barang Harus Diisi")
    @Column(nullable = false, name = "namaBarang")
    private String namaBarang;

    @NotEmpty(message = "Jumlah Barang Harus Diisi")
    @Column(nullable = false, name = "jumlahBarang")
    private Long jumlahBarang;

    @NotEmpty(message = "Harga Barang Harus Diisi")
    @Column(nullable = false, name = "hargaBarang")
    private Long hargaBarang;
}
