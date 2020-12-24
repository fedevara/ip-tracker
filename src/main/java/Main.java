import freemarker.template.Configuration;
import model.IpInformationResponse;
import model.StatisticResponse;
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
    private static final String ERROR_VIEW = "error.ftl";

    public static void main(String[] args) {

        Spark.staticFiles.location("/views");
        Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_0);
        freemarkerConfiguration.setClassForTemplateLoading(Main.class, "/views");
        SingletonCountries.getInstance();

        Spark.port(8079);
        Spark.get("/trackip/search", (request, response) -> trackIP(request, response), new FreeMarkerEngine(freemarkerConfiguration));
        Spark.get("/trackip/report", (request, response) -> report(request, response), new FreeMarkerEngine(freemarkerConfiguration));
    }

    private static ModelAndView trackIP(Request request, Response response) {
        String ip = request.queryParams("ip");
        Map<String, Object> model = new HashMap<>();
        if (isInvalidIp(ip)) {
            model.put("error", "La IP ingresada no es válida");
            return new ModelAndView(model, ERROR_VIEW);
        } else {
            IpInformationResponse ipInformation = ReportService.retrieveIpInformation(ip);
            model.put("ipInformation", ipInformation);
            return new ModelAndView(model, TRACEIP_VIEW);
        }
    }

    private static boolean isInvalidIp(String ip) {
        return !ip.matches("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b");
    }

    private static ModelAndView report(Request request, Response response) {
        StatisticResponse statistic = ReportService.calculateDistanceInfo();
        Map<String, Object> model = new HashMap<>();
        if (statistic != null) {
            model.put("statistic", statistic);
            return new ModelAndView(model, REPORT_VIEW);
        } else {
            model.put("error", "Aun no se han realizado consultas");
            return new ModelAndView(model, ERROR_VIEW);
        }
    }

}
