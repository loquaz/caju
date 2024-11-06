package application.controller

import application.http.PaymentAuthorizerApplication
import domain.usecase.IFallbackAuthorizationUseCase
import domain.usecase.ISimpleAuthorizationUseCase
import com.fasterxml.jackson.module.kotlin.jsonMapper
import configuration.TestConfiguration
import application.http.controller.ExceptionAdviceController
import application.http.controller.dto.TransactionRequestDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TestConfiguration::class, ExceptionAdviceController::class, PaymentAuthorizerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc //need this in Spring Boot test
class AuthorizerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var simpleAuthorizationUseCase: ISimpleAuthorizationUseCase

    @Autowired
    lateinit var fallAuthorizationUseCase: IFallbackAuthorizationUseCase

    /*
    POST /authorizer/simple
     */
    @Test
    fun `test must success on withdraw 50 reais from FOOD balance`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(50),5411,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "00" )
       post("/authorizer/simple", request, expected)
    }

    @Test
    fun `test must fail on not funds from MEAL balance`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(45.001),5811,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "51" )
        post("/authorizer/simple", request, expected)
    }

    @Test
    fun `test must fail on not funds from FOOD balance`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(51.01),5411,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "51" )
        post("/authorizer/simple", request, expected)
    }

    @Test
    fun `test must fail on nonexistent MCC`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(51.01),
            TestsUtil.INEXISTENT_MCC,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "07" )
        post("/authorizer/simple", request, expected)
    }

    /**
     * conta nao encontrada
     */
    @Test
    fun `test must fail on ACCOUNT NOT FOUND given a wrong accountId`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_NOT_FOUND_ID),BigDecimal(51.01),5411,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "07" )
        post("/authorizer/simple", request, expected)
    }

    /**
     * saldo insuficiente
     */

    /*
    POST /authorizer/fallback
     */
    @Test
    fun `test must success on withdraw 50 reais from FOOD balance fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(50),5411,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "00" )
        post("/authorizer/fallback", request, expected)
    }

    @Test
    fun `test must fail on withdraw 55 reais from CASH balance giving FOOD MCC fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(55),5411,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "51" )
        post("/authorizer/fallback", request, expected)
    }

    @Test
    fun `test must fail on withdraw 61 reais from CASH balance giving FOOD MCC fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(61),5411,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "51" )
        post("/authorizer/fallback", request, expected)
    }

    @Test
    fun `test must success on withdraw 44,9 reais from MEAL balance fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(44.9),5811,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "00" )
        post("/authorizer/fallback", request, expected)
    }

    @Test
    fun `test must fail on withdraw 46 reais from CASH balance giving FOOD MCC fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(61),5811,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "51" )
        post("/authorizer/fallback", request, expected)
    }

    @Test
    fun `test must fail on withdraw 60,001 reais from CASH balance giving FOOD MCC fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(60.001),5811,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "51" )
        post("/authorizer/fallback", request, expected)
    }

    @Test
    fun `test must fail on ACCOUNT NOT FOUND given a wrong accountId, fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_NOT_FOUND_ID),BigDecimal(51.01),5411,"PADARIA DO ZE")
        val expected    = buildExpectedResponseObject( "07" )
        post("/authorizer/fallback", request, expected)
    }

    @Test
    fun `test must fail on NOT FOUND WALLET TYPE given a wrong MCC and merchant not identified, fallback`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(51.01),5501,"NEGOCIO NAO MAPEADO")
        val expected    = buildExpectedResponseObject( "00" )
        post("/authorizer/fallback", request, expected)
    }

    /*
    POST /authorizer/merchant-dependent
     */
    @Test
    fun `test must fail on withdraw 60,001 reais from FOOD balance merchant dependent`() {
        val request     = TransactionRequestDTO( UUID.fromString(TestsUtil.ACCOUNT_ID),BigDecimal(60.001),6000,"UBER TRIP")
        val expected    = buildExpectedResponseObject( "51" )
        post("/authorizer/merchant-dependent", request, expected)
    }

    private fun post(path: String, request: TransactionRequestDTO, expect: String){
        mockMvc.post(path){
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper().writeValueAsString( request )
            accept  = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON)   }
            content { json( expect ) }
        }
    }

    private fun buildExpectedResponseObject(value: String) : String {
        return "{\"code\":\"${value}\"}"
    }
}