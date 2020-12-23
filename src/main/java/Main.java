import freemarker.template.Configuration;
import model.IpInformationResponse;
import model.StatisticResponse;
import org.eclipse.jetty.http.HttpStatus;
import service.ReportService;
import service.SingletonCountries;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String TRACEIP_VIEW = "ipTrack.ftl";
    private static final String REPORT_VIEW = "report.ftl";

    public static void main(String[] args) {
        Spark.staticFiles.location("/views");
        Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_0);
        freemarkerConfiguration.setClassForTemplateLoading(Main.class, "/views");
        init();

        Spark.port(8079);
        Spark.get("/trackip/search", (request, response) -> trackIP(request, response), new FreeMarkerEngine(freemarkerConfiguration));
        Spark.get("/trackip/report", (request, response) -> report(request, response), new FreeMarkerEngine(freemarkerConfiguration));
    }

    private static void init() {
        SingletonCountries.getInstance();
    }

    private static ModelAndView trackIP(Request request, Response response) {
        String ip = request.queryParams("ip");

        IpInformationResponse ipInformation = ReportService.retrieveIpInformation(ip);

        Map<String, Object> model = new HashMap<>();
        model.put("ipInformation", ipInformation);
        return new ModelAndView(model, TRACEIP_VIEW);
    }

    private static ModelAndView report(Request request, Response response) {
        StatisticResponse statistic = ReportService.calculateDistanceInfo();
        Map<String, Object> model = new HashMap<>();
        model.put("statistic", statistic);
        return new ModelAndView(model, REPORT_VIEW);
    }

}
