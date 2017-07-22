package com.epam.katowice.service;

import com.epam.katowice.domain.Actor;
import com.epam.katowice.dto.ActorTo;

import java.util.List;

public interface ActorService {
    List<ActorTo> listAll();

    Actor getOne(Long id);
}
