package com.epam.katowice.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;

import com.epam.katowice.configuration.security.Role;
import com.epam.katowice.domain.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class SpringDataUserDetailsServiceTest {

    private static final String EXISTING_USER_NAME = "existingUserName";
    private static final String EXISTING_USER_PASSWORD = "existingUserPassword";
    private static final String NOT_EXISTING_USER_NAME = "notExistingUserName";
    private static final int ROLES_COLLECTION_SIZE = 2;

    private UserInfo userInfo = new UserInfo();

    @Mock
    private UserInfoService userInfoService;

    @InjectMocks
    private UserDetailsService userDetailsService = new SpringDataUserDetailsService();

    @Before
    public void setUp() {
        userInfo.setEnabled(true);
        userInfo.setUserName(EXISTING_USER_NAME);
        userInfo.setPassword(EXISTING_USER_PASSWORD);

        Set<String> roles = new HashSet<>();
        roles.add(Role.ADMIN.name());
        roles.add(Role.USER.name());
        userInfo.setRoles(roles);

    }

    @Test
    public void shouldLoadUserByUsername() {
        //given
        given(userInfoService.findByName(EXISTING_USER_NAME)).willReturn(userInfo);

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(EXISTING_USER_NAME);

        //then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isNotNull().isEqualTo(EXISTING_USER_NAME);
        assertThat(userDetails.getPassword()).isNotNull().isEqualTo(EXISTING_USER_PASSWORD);
        Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
        assertThat(grantedAuthorities).isNotNull().isNotEmpty().hasSize(ROLES_COLLECTION_SIZE);

        List<String>
            authorities =
            grantedAuthorities.stream().map(role -> role.getAuthority()).collect(Collectors.toList());
        assertThat(authorities).containsOnly(Role.ADMIN.name(), Role.USER.name());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExceptionWhenUserCannotBeFound() {

        //given
        given(userInfoService.findByName(NOT_EXISTING_USER_NAME)).willReturn(null);

        //when
        userDetailsService.loadUserByUsername(NOT_EXISTING_USER_NAME);

        //then
        fail();
    }


}