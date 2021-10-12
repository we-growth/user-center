package cn.wegrowth.usercenter.auth.sms

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AccountStatusException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.oauth2.provider.*
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices

class SMSCodeTokenGranter(
    private val authenticationManager: AuthenticationManager,
    tokenServices: AuthorizationServerTokenServices,
    clientDetailsService: ClientDetailsService,
    requestFactory: OAuth2RequestFactory,
) : AbstractTokenGranter(tokenServices, clientDetailsService, requestFactory, "sms_code") {


    override fun getOAuth2Authentication(client: ClientDetails, tokenRequest: TokenRequest): OAuth2Authentication {
        val parameters: Map<String, String> = LinkedHashMap(tokenRequest.requestParameters)
        val mobile = parameters["mobile"]
        val code = parameters["code"]

        if (mobile == null)
            throw InvalidGrantException("mobile number is required")

        if (code == null)
            throw InvalidGrantException("mobile auth code is required")

        var userAuth: Authentication = SmsCodeAuthenticationToken(mobile, code)
        (userAuth as AbstractAuthenticationToken).details = parameters

        try {
            userAuth = authenticationManager.authenticate(userAuth)
        } catch (ase: AccountStatusException) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw InvalidGrantException(ase.message)
        } catch (e: BadCredentialsException) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw InvalidGrantException(e.message)
        } catch (e: UsernameNotFoundException) {
            // If the user is not found, report a generic error message
            throw InvalidGrantException(e.message)
        }

        if (userAuth == null || !userAuth.isAuthenticated) {
            throw InvalidGrantException("Could not authenticate mobile: $mobile")
        }

        val storedOAuth2Request = requestFactory.createOAuth2Request(client, tokenRequest)
        return OAuth2Authentication(storedOAuth2Request, userAuth)
    }
}