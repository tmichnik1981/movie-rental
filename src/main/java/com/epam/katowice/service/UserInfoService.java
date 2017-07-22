package com.epam.katowice.service;

import com.epam.katowice.domain.UserInfo;

public interface UserInfoService {

    UserInfo findByName(String name);
}
