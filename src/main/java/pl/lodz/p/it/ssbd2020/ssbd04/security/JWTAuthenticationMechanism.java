package pl.lodz.p.it.ssbd2020.ssbd04.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementuje mechanizm uwierzytelniający i autoryzujący
 * na podstawie JWT wysyłanego w każdym nadchodzącym żądaniu.
 */
@RequestScoped
public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final Logger LOGGER = Logger.getLogger(JWTAuthenticationMechanism.class.getName());
    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER = "Bearer ";

    @Inject
    private JWTProvider jwtProvider;

    @Inject
    private Config config;

    /**
     * Pobiera JWT z nagłówka żądania, a następnie:
     * jeśli zasób jest chroniony i żądanie nie zawiera nagłówka, bądź nagłówek jest niepoprawny to zwraca 404 NotFound,
     * jeśli zasób jest chroniony i żądanie posiada poprawny nagłówek, deleguje sprawdzanie uprawnień do kontenera,
     * jeśli zasób nie jest chroniony, token nie jest sprawdzany.
     *
     * @param request
     * @param response
     * @param context
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
        JWT jwt = extractToken(request);
        if (jwt != null) {
            return CORS(context).notifyContainerAboutLogin(jwt.getPrincipal(), jwt.getAuthorities());
        } else if (!context.isProtected()) {
            return CORS(context).doNothing();
        }

        return CORS(context).responseNotFound();
    }

    private HttpMessageContext CORS(HttpMessageContext context) {
        context.getResponse().setHeader("Access-Control-Allow-Origin", config.getFrontendURL());
        context.getResponse().setHeader("Access-Control-Allow-Headers", "*");
        context.getResponse().setHeader("Access-Control-Allow-Credentials", "true");
        context.getResponse().setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        context.getResponse().setHeader("Access-Control-Max-Age", "1209600");
        return context;
    }

    private JWT extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            return null;
        }

        try {
            return jwtProvider.load(extractToken(authorizationHeader));
        } catch (SignatureException e) {
            LOGGER.log(Level.INFO, "Invalid JWT signature {0}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.log(Level.INFO, "Expired JWT {0}", e.getMessage());
        }

        return null;
    }

    public static String extractToken(String requestContext){
        return requestContext.substring(BEARER.length());
    }


}
