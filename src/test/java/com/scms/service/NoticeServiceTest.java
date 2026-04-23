package com.scms.service;

import com.scms.model.Notice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NoticeService Tests")
public class NoticeServiceTest {

    private NoticeService noticeService;

    @BeforeEach
    void setUp() {
        noticeService = new NoticeService();
    }

    @Test
    @DisplayName("Should throw exception for empty title")
    void testPostNoticeEmptyTitle() {
        Notice notice = new Notice();
        notice.setTitle("");
        notice.setContent("Some content");
        notice.setPostedBy(1);

        assertThrows(IllegalArgumentException.class, () -> {
            noticeService.postNotice(notice);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty content")
    void testPostNoticeEmptyContent() {
        Notice notice = new Notice();
        notice.setTitle("Test Notice");
        notice.setContent("");
        notice.setPostedBy(1);

        assertThrows(IllegalArgumentException.class, () -> {
            noticeService.postNotice(notice);
        });
    }

    @Test
    @DisplayName("Should throw exception for null title")
    void testPostNoticeNullTitle() {
        Notice notice = new Notice();
        notice.setTitle(null);
        notice.setContent("Some content");
        notice.setPostedBy(1);

        assertThrows(IllegalArgumentException.class, () -> {
            noticeService.postNotice(notice);
        });
    }

    @Test
    @DisplayName("Should create valid notice object")
    void testCreateValidNotice() {
        Notice notice = new Notice();
        notice.setNoticeId(1);
        notice.setTitle("Holiday Announcement");
        notice.setContent("College will be closed on XYZ date");
        notice.setPostedBy(5);

        assertEquals(1, notice.getNoticeId());
        assertEquals("Holiday Announcement", notice.getTitle());
        assertNotNull(notice.getContent());
        assertEquals(5, notice.getPostedBy());
    }
}