package com.api.user.service;

import com.api.user.entity.Contract;
import com.api.user.entity.Feedback;
import com.api.user.entity.info.ContractInfo;

import java.util.List;

public interface ContractService {
    int save(Contract contract);

    Contract findById(int contract_id);

    void update(Contract contract);

    List<Contract> listContractByStudentId(Integer id);

    List<Contract> listContractByTutorId(Integer id);

    ContractInfo detailContract(int id);

    List<Contract> listContractByTime(Integer id, long date_from, long date_to);

    //Feedback
    void addFeedback(Feedback feedback);


}
