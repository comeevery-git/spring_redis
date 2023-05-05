package com.example.concu.domain.applier.service;

import com.example.concu.domain.applier.dto.ReqApply;

public interface ApplierService {
    Long apply(ReqApply reqApply) throws Exception;

}
