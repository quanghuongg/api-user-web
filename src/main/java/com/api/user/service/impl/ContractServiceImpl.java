package com.api.user.service.impl;

import com.api.user.entity.Contract;
import com.api.user.entity.Feedback;
import com.api.user.entity.User;
import com.api.user.entity.info.ContractInfo;
import com.api.user.mapper.ContractMapper;
import com.api.user.mapper.FeedbackMapper;
import com.api.user.mapper.UserMapper;
import com.api.user.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service(value = "contractService")
public class ContractServiceImpl implements ContractService {
    private ContractMapper contractMapper;
    private UserMapper userMapper;
    private FeedbackMapper feedbackMapper;

    @Autowired
    public ContractServiceImpl(ContractMapper contractMapper, UserMapper userMapper, FeedbackMapper feedbackMapper) {
        this.contractMapper = contractMapper;
        this.userMapper = userMapper;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public int save(Contract contract) {
        contractMapper.createContract(contract);
        return contract.getId();
    }

    @Override
    public Contract findById(int contract_id) {
        return contractMapper.findById(contract_id);
    }

    @Override
    public void update(Contract contract) {
        contractMapper.update(contract);
    }

    @Override
    public List<Contract> listContractByStudentId(Integer id) {
        return contractMapper.listContractByStudentId(id);
    }

    @Override
    public List<Contract> listContractByTutorId(Integer id) {
        return contractMapper.listContractByTutorId(id);
    }

    @Override
    public ContractInfo detailContract(int id) {
        Contract contract = contractMapper.findById(id);
        ContractInfo contractInfo = new ContractInfo(contract);
        User tutor = userMapper.findByUserId(contract.getTutor_id());
        contractInfo.setTutorName(tutor.getDisplay_name());
        return contractInfo;
    }

    @Override
    public List<Contract> listRevenueByTime(Integer id, long date_from, long date_to) {
        return contractMapper.listRevenueByTime(id, date_from, date_to);
    }

    @Override
    public List<Contract> listRevenues(Integer userId) {
        return contractMapper.listRevenues(userId);
    }

    @Override
    public void addFeedback(Feedback feedback) {
        feedbackMapper.addFeedback(feedback);
    }
}
