package com.example.concu.applier.domain.service;

import com.example.concu.applier.web.dto.ReqApply;

public interface ApplierService {
    Long apply(ReqApply reqApply) throws Exception;

}
