package com.epam.katowice.service;

import com.epam.katowice.MovieRentalApplication;
import com.epam.katowice.domain.Actor;
import com.epam.katowice.dto.ActorTo;
import com.epam.katowice.mapping.ActorMapper;
import com.epam.katowice.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(propagation = Propagation.SUPPORTS,
    readOnly = true)
@Service
public class ActorServiceImpl implements ActorService {

    private ActorRepository actorRepository;
    private ActorMapper actorMapper;

    @Cacheable(value = MovieRentalApplication.ACTORS_CACHE)
    @Override
    public List<ActorTo> listAll() {

        List<ActorTo>  actorTos =
            actorRepository.findAll().stream().map(actor -> actorMapper.asDto(actor)).collect(
                Collectors.toList());

        return actorTos;
    }

    @Override
    public Actor getOne(Long id) {
        return actorRepository.getOne(id);
    }

    @Autowired
    public void setActorRepository(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Autowired
    public void setActorMapper(ActorMapper actorMapper) {
        this.actorMapper = actorMapper;
    }
}
