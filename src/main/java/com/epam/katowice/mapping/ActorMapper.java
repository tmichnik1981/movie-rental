package com.epam.katowice.mapping;


import com.epam.katowice.domain.Actor;
import com.epam.katowice.dto.ActorTo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ActorMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public ActorTo asDto(Actor actorEntity) {
        return modelMapper.map(actorEntity, ActorTo.class);
    }
}
