package com.example.concu.presentation.applier;

import com.example.concu.presentation.applier.dto.ReqApply;
import com.example.concu.domain.applier.service.ApplierService;
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
