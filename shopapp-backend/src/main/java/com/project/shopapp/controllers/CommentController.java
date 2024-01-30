package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.dtos.CommentDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Comment;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.CommentResponse;
import com.project.shopapp.responses.UpdateCategoryResponse;
import com.project.shopapp.services.comment.CommentService;
import com.project.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("")
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @RequestParam(value = "userId", required = false)  Long userId,
            @RequestParam("productId")    long productId
    ) {
        List<CommentResponse> commentResponses;
        if(userId == null) {
            commentResponses = commentService.getCommentsByProduct(productId);
        }else{
            commentResponses = commentService.getCommentsByUserAndProduct(userId,productId);
        }
        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> insertComment(
            @Valid @RequestBody CommentDTO commentDTO,
            Authentication authentication
    ) {
        try {
            User lgoinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           if(lgoinUser.getId() != commentDTO.getUserId()) {
               return ResponseEntity.badRequest().body("Has error");
           }
            commentService.insertComment(commentDTO);
            return ResponseEntity.ok("Insert comment successfully");
        }catch (Exception e) {
            return ResponseEntity.ok("Has error");
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentDTO commentDTO,
            Authentication authentication
    ) {
       try {
           User lgoinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              if(lgoinUser.getId() != commentDTO.getUserId()) {
                return ResponseEntity.badRequest().body("You can not update another user's comment");
              }
           commentService.updateComment(commentId,commentDTO);
           return ResponseEntity.ok("Update comment successfully");
       }catch (Exception e) {
           return ResponseEntity.ok("Has error");
       }

    }
}
