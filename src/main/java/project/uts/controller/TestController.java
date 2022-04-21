package project.uts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.uts.entity.Transaksi;

import javax.validation.Valid;

@Controller
public class TestController {
    @GetMapping("/test1")
    public String getForm(Transaksi transaksi) {
        return "index2";
    }

    @PostMapping("/save-transaksi")
    public String submitTransaksi(@Valid Transaksi transaksi, Errors errors, Model model) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "index2";
        }
        else {
            model.addAttribute("successMsg", "Detail Tersimpan");
            return "success";
        }
    }
}
