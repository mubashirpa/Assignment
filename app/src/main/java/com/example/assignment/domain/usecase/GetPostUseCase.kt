package com.example.assignment.domain.usecase

import com.example.assignment.core.utils.Resource
import com.example.assignment.core.utils.UiText
import com.example.assignment.data.mapper.toPostResultModel
import com.example.assignment.data.remote.service.AssignmentService
import com.example.assignment.domain.models.PostResultModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.assignment.R.string as Strings

class GetPostUseCase @Inject constructor(
    private val assignmentService: AssignmentService
) {
    operator fun invoke(postId: String): Flow<Resource<PostResultModel>> = flow {
        try {
            emit(Resource.Loading())
            val details = assignmentService.getPost(postId).toPostResultModel()
            emit(Resource.Success(details))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(Strings.error_no_internet)))
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.StringResource(Strings.error_unexpected)))
        } catch (e: Exception) {
            emit(Resource.Error(UiText.StringResource(Strings.error_unknown)))
        }
    }
}