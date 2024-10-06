package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.ReactionRequestDTO;
import com.abhijeet.commentsService.models.dto.response.ReactionResponseDTO;
import com.abhijeet.commentsService.models.entity.Reaction;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import com.abhijeet.commentsService.service.UserService;
import com.abhijeet.commentsService.util.HeaderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReactionMapperTest {

    @Mock
    private HeaderUtil headerUtil;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReactionMapper reactionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFromDTO() {
        Long commentId = 1L;
        ReactionRequestDTO reactionRequestDTO = ReactionRequestDTO.builder()
                .reactionType(ReactionType.LIKES)
                .reactionEntityType(ReactionEntityType.COMMENT)
                .build();

        when(headerUtil.getUserId()).thenReturn(42L);
        Reaction reaction = reactionMapper.fromDTO(commentId, reactionRequestDTO);

        assertEquals(ReactionType.LIKES, reaction.getReactionType());
        assertEquals(ReactionEntityType.COMMENT, reaction.getReactionEntityType());
        assertEquals(42L, reaction.getUserId());
        assertEquals(commentId, reaction.getEntityId());
    }

    @Test
    public void testToDTO() {
        Reaction reaction = new Reaction();
        reaction.setUserId(42L);
        reaction.setEntityId(1L);

        User user = new User();
        user.setId(42L);
        user.setFirstName("asd");
        user.setLastName("qwe");
        user.setUsername("cvb");

        when(userService.getUserById(42L)).thenReturn(user);

        ReactionResponseDTO reactionResponseDTO = reactionMapper.toDTO(reaction);

        assertEquals(42L, reactionResponseDTO.getUserId());
        assertEquals(1L, reactionResponseDTO.getEntityId());
        assertEquals("asd", reactionResponseDTO.getFirstName());
        assertEquals("qwe", reactionResponseDTO.getLastName());
        assertEquals("cvb", reactionResponseDTO.getUserName());
    }
}
