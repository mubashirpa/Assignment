package com.example.assignment.data.mapper

import com.example.assignment.data.remote.dto.comments.CommentResult
import com.example.assignment.domain.models.CommentResultModel

fun CommentResult.toCommentResultModel(): CommentResultModel {
    return CommentResultModel(commentId, content, likes, postId, timestamp, user, userId)
}