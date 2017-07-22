package com.epam.katowice.service;

import com.epam.katowice.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        UserInfo  userInfo = userInfoService.findByName(name);

        if(userInfo == null) {
            throw new UsernameNotFoundException(String.format("User with name: %s cannot be found", name));
        }
        String[] roles = new String[userInfo.getRoles().size()];
        userInfo.getRoles().toArray(roles);

        UserDetails userDetails =  new User(name, userInfo.getPassword(),
                        AuthorityUtils.createAuthorityList(roles));
        return userDetails;
    }

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
}
