package com.flightdetails.Service;

import com.flightdetails.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, long postId);

    List<CommentDto> listAllComments(long postId);

    CommentDto getCommentById(long postId, long id);

    CommentDto updateComment(CommentDto commentDto, long postId, long id);

    void deleteComment(long id, long postId);
}
