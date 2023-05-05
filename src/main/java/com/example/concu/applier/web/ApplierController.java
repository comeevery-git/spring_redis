package com.example.concu.applier.web;

import com.example.concu.applier.domain.service.ApplierService;
import com.example.concu.applier.web.dto.ReqApply;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applier")
public class ApplierController {
    private final ApplierService applierService;


    @PostMapping("/apply")
    public Long apply(@RequestBody ReqApply reqApply) throws Exception {
        Long totalApplier = applierService.apply(reqApply);
        return totalApplier;
    }

}
