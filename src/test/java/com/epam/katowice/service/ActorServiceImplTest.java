package com.epam.katowice.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.katowice.domain.Actor;
import com.epam.katowice.dto.ActorTo;
import com.epam.katowice.mapping.ActorMapper;
import com.epam.katowice.repository.ActorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ActorServiceImplTest {

    private static final long ACTOR_ID_1 = 1, ACTOR_ID_2 = 2,
        ACTOR_ID_3 = 3;
    private static final int EXPECTED_ACTORS_RESULT_SIZE = 3;

    private Actor actor1 = new Actor(), actor2 = new Actor(), actor3 = new Actor();
    private ActorTo actorTo1 = new ActorTo(), actorTo2 = new ActorTo(), actorTo3 = new ActorTo();

    @Mock
    private ActorRepository actorRepository;
    @Mock
    private ActorMapper actorMapper;

    @InjectMocks
    private ActorService actorService = new ActorServiceImpl();

    @Before
    public void setUp(){
        actor1.setId(ACTOR_ID_1);
        actor2.setId(ACTOR_ID_2);
        actor3.setId(ACTOR_ID_3);

        actorTo1.setId(ACTOR_ID_1);
        actorTo2.setId(ACTOR_ID_2);
        actorTo3.setId(ACTOR_ID_3);
    }

    @Test
    public void shouldFindAllActors() throws Exception {
        //given
        when(actorRepository.findAll()).thenReturn(Arrays.asList(actor1, actor2, actor3));
        when(actorMapper.asDto(actor1)).thenReturn(actorTo1);
        when(actorMapper.asDto(actor2)).thenReturn(actorTo2);
        when(actorMapper.asDto(actor3)).thenReturn(actorTo3);

        //when
        List<ActorTo> actorTosResult = actorService.listAll();

        //then
        assertThat(actorTosResult).isNotNull().isNotEmpty().hasSize(EXPECTED_ACTORS_RESULT_SIZE)
            .containsExactly(actorTo1, actorTo2, actorTo3);
    }

    @Test
    public void shouldNotFindAnyActorsWhenNoActorsInRepository() throws Exception {
        //given
        when(actorRepository.findAll()).thenReturn(Collections.<Actor>emptyList());
        //when
        List<ActorTo> actorTosResult = actorService.listAll();

        //then
        assertThat(actorTosResult).isNotNull().isEmpty();
    }

    @Test
    public void shouldFindOneActor() {
        //given
        when(actorRepository.getOne(ACTOR_ID_1)).thenReturn(actor1);

        //when
        Actor actorResult = actorService.getOne(ACTOR_ID_1);

        //then
        assertThat(actorResult).isNotNull().isEqualTo(actor1);
    }

    @Test
    public void shouldNotFindActorWithGivenIdWhenNotExistsInRepository() {
        //given
        when(actorRepository.getOne(ACTOR_ID_1)).thenReturn(null);

        //when
        Actor actorResult = actorService.getOne(ACTOR_ID_1);

        //then
        assertThat(actorResult).isNull();
    }
}