package domain.usecase

import com.caju.entity.AuthorizationRequestEntity
import com.caju.entity.AuthorizationResponseEntity

interface IMerchantDependentUseCase {
    fun exec(authorizationRequest: AuthorizationRequestEntity) : AuthorizationResponseEntity
}