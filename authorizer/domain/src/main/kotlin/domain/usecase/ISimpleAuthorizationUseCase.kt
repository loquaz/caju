package domain.usecase

import com.caju.entity.AuthorizationRequestEntity
import com.caju.entity.AuthorizationResponseEntity

interface ISimpleAuthorizationUseCase {
    fun exec(authorizationRequest: AuthorizationRequestEntity) : AuthorizationResponseEntity
}