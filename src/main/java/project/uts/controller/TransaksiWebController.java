package project.uts.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.uts.entity.Transaksi;
import project.uts.service.TransaksiService;

@Controller
@AllArgsConstructor
public class TransaksiWebController {
    private TransaksiService transaksiService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("transaksi", transaksiService.getAllTransaksi());
        return "index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("transaksi", new Transaksi());
        return "formTransaksi";
    }

    @PostMapping(value = "/create")
    public String tambahTransaksi(Model model, Transaksi transaksi) {
        model.addAttribute("transaksi", transaksiService.save(transaksi));
        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("transaksi", transaksiService.findById(id));
        return "formTransaksi";
    }

    @GetMapping(value = "/hapus/{id}")
    public String hapusTransaksi(@PathVariable Long id) {
        transaksiService.deleteById(id);
        return "redirect:/";
    }
}
