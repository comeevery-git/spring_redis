package com.example.ticket.concurrency.domain.applier.controller;

import com.example.ticket.concurrency.domain.applier.entity.request.ReqApply;
import com.example.ticket.concurrency.domain.applier.service.ApplierService;
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

    @PostMapping("/base")
    public void applyBase() throws Exception {
        applierService.applyBase();
    }

    @PostMapping("/apply")
    public Long apply(@RequestBody ReqApply reqApply) throws Exception {
        Long totalApplier = applierService.apply(reqApply);

        return totalApplier;
    }


}
