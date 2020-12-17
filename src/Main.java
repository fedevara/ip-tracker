import service.ReportService;
import service.SingletonCountries;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Main {

    public static void main(String[] args) {

        init();

        Spark.port(8080);
        Spark.get("/trackip/:ip", (request, response) -> trackIP(request, response));
        Spark.get("/trackip/report", (request, response) -> report(request, response));

    }

    private static void init() {

        SingletonCountries.getInstance();

    }

    private static Object trackIP(Request request, Response response) {

        return ReportService.retrieveIpInformation(request.queryParams("ip"));

    }

    private static Object report(Request request, Response response) {

        //TODO
        return null;

    }

}
