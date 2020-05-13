package pl.lodz.p.it.ssbd2020.ssbd04;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Obsługuje żądania, które nie pochodzą z interfejsu web aplikacji.
 * W przypadku odwołań do /api zwraca komunikat o błędzie,
 * a dla każdego innego przekierowuje żądanie do routera znajdującego się w aplikacji React.
 * Klasa potrzebna ze względu na specyfike wdrożenia aplikacji (frontend, backend) w jednym archiwum WAR.
 */
@WebServlet("/exception_handler")
public class AppExceptionHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        String contextPath = request.getContextPath();

        if (requestUri.equals(contextPath + "/api") || requestUri.startsWith(contextPath + "/api/")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("status", statusCode);

            String message = "Unknown error";
            if (statusCode == 404) {
                message = "Resource not found";
            } else if (throwable != null) {
                message = throwable.getMessage();
            }
            builder.add("message", message);

            response.getWriter().print(builder.build());
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }
}
