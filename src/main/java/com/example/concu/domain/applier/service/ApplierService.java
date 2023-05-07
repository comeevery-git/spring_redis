package com.example.concu.domain.applier.service;

import com.example.concu.presentation.dto.ReqApply;

public interface ApplierService {
    Long apply(ReqApply reqApply) throws Exception;

}
