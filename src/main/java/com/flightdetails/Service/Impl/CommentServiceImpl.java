package com.flightdetails.Service.Impl;

import com.flightdetails.Entity.Comment;
import com.flightdetails.Entity.Post;
import com.flightdetails.Exception.ResourceNotFoundException;
import com.flightdetails.Payload.CommentDto;
import com.flightdetails.Repository.CommentRepository;
import com.flightdetails.Repository.PostRepository;
import com.flightdetails.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Postnot found with id:" + postId)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setId(postId);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> listAllComments(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Postnot found with id:" + postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Postnot found with id:" + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + id)
        );
        if (comment.getPost().getId()!=postId){
           throw new ResourceNotFoundException("Comment not belong specified postId:"+postId);
        }
        CommentDto dto = mapToDto(comment);
        return dto;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Postnot found with id:" + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + id)
        );
        if (comment.getPost().getId()!=postId){
            throw new ResourceNotFoundException("Comment not belong specified postId:"+postId);
        }
        comment.setName(commentDto.getName());
        comment.setEmail(comment.getEmail());
        comment.setBody(comment.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long id, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Postnot found with id:" + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + id)
        );
        if (comment.getPost().getId()!=postId){
            throw new ResourceNotFoundException("Comment not belong specified postId:"+postId);
        }
        commentRepository.deleteById(id);
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

}
