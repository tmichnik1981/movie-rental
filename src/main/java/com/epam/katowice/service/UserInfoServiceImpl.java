package com.epam.katowice.service;

import com.epam.katowice.domain.UserInfo;
import com.epam.katowice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS,
    readOnly = true)
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByName(String name) {
        return userInfoRepository.findByUserName(name);
    }

    @Autowired
    public void setUserInfoRepository(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
}
