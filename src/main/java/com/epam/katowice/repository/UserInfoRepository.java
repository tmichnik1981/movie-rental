package com.epam.katowice.repository;

import com.epam.katowice.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUserName(String name);
}
