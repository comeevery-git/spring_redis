package com.example.ticket.concurrency.domain.applier.service;

import com.example.ticket.concurrency.domain.applier.entity.request.ReqApply;

public interface ApplierService {
    Long apply(ReqApply reqApply) throws Exception;

}
