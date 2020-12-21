import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import service.ReportService;
import service.SingletonCountries;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Main {

    public static void main(String[] args) {

        init();

        Spark.port(8080);
        Spark.get("/trackip/search/:ip", (request, response) -> trackIP(request, response));
        Spark.get("/trackip/report", (request, response) -> report(response));

    }

    private static void init() {

        SingletonCountries.getInstance();

    }

    private static Object trackIP(Request request, Response response) {

        response.status(HttpStatus.OK_200);
        response.header(HttpHeader.CONTENT_TYPE.toString(), MimeTypes.Type.APPLICATION_JSON_UTF_8.toString());

        return ReportService.retrieveIpInformation(request.params("ip"));

    }

    private static Object report(Response response) {

        response.status(HttpStatus.OK_200);
        response.header(HttpHeader.CONTENT_TYPE.toString(), MimeTypes.Type.APPLICATION_JSON_UTF_8.toString());

        return ReportService.calculateDistanceInfo();

    }

}
