package edu.xjtu.social;

import edu.xjtu.social.controller.ShareController;
import edu.xjtu.social.dao.ShareDao;
import edu.xjtu.social.domain.util.ResponseInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SocialRecommendApplicationTests {

    @Mock
    private ShareDao shareDao;

    @InjectMocks
    private ShareController shareController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikeShareSuccess() {
        // Mocking the response from the ShareDao
        when(shareDao.likeShare("user1", 1L)).thenReturn(1);
    
        // Calling the controller method
        ResponseInfo response = shareController.likeShare("user1", 1L);
    
        // Verifying the response
        assertTrue(response.getOk());  // Changed from isSuccess() to getOk()
        assertEquals("success", response.getMsg());  // Changed from getMessage() to getMsg()
        verify(shareDao, times(1)).likeShare("user1", 1L);
    }
    
    @Test
    void testLikeShareFailure() {
        // Mocking the response from the ShareDao
        when(shareDao.likeShare("user1", 1L)).thenReturn(0);
    
        // Calling the controller method
        ResponseInfo response = shareController.likeShare("user1", 1L);
    
        // Verifying the response
        assertFalse(response.getOk());  // Changed from isSuccess() to getOk()
        assertEquals("fail", response.getMsg());  // Changed from getMessage() to getMsg()
        verify(shareDao, times(1)).likeShare("user1", 1L);
    }
    
}
