package com.epam.katowice.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.epam.katowice.domain.UserInfo;
import com.epam.katowice.repository.UserInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserInfoServiceImplTest {

    private static final String EXISTING_USER_NAME = "existingUserName";
    private static final String NOT_EXISTING_USER_NAME = "notExistingUserName";

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserInfoService userInfoService = new UserInfoServiceImpl();

    @Test
    public void shouldNotFindUserInfoWhenNotExistInRepository() {
        //given
        given(userInfoRepository.findByUserName(NOT_EXISTING_USER_NAME)).willReturn(null);

        //when
        UserInfo userInfoResult = userInfoService.findByName(EXISTING_USER_NAME);

        //then
        assertThat(userInfoResult).isNull();
    }

    @Test
    public void shouldFindUserInfoByName() {
        //given
        UserInfo userInfo = mock(UserInfo.class);
        given(userInfoRepository.findByUserName(EXISTING_USER_NAME)).willReturn(userInfo);

        //when
        UserInfo userInfoResult = userInfoService.findByName(EXISTING_USER_NAME);

        //then
        assertThat(userInfoResult).isNotNull().isEqualTo(userInfo);

    }

}