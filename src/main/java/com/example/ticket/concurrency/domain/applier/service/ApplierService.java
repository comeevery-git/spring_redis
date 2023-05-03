package com.example.ticket.concurrency.domain.applier.service;

import com.example.ticket.concurrency.domain.applier.entity.Applier;
import com.example.ticket.concurrency.domain.applier.entity.request.ReqApply;

import java.util.List;

public interface ApplierService {

    Long apply(ReqApply reqApply) throws Exception;

}
