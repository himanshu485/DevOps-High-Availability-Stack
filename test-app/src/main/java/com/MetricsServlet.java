package com.example;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Exposes basic application metrics in Prometheus text exposition format.
 * Prometheus scrapes this endpoint at /metrics.
 *
 * Format reference: https://prometheus.io/docs/instrumenting/exposition_formats/
 */
@WebServlet("/metrics")
public class MetricsServlet extends HttpServlet {

    private static final String INSTANCE =
            System.getenv().getOrDefault("INSTANCE_NAME", "Unknown");

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/plain; version=0.0.4; charset=utf-8");

        PrintWriter out = resp.getWriter();

        long requestCount = AppMetrics.REQUEST_COUNT.get();
        long dbErrorCount = AppMetrics.DB_ERROR_COUNT.get();
        long totalTimeMs = AppMetrics.TOTAL_RESPONSE_TIME_MS.get();

        double avgResponseSeconds = requestCount > 0
                ? (totalTimeMs / (double) requestCount) / 1000.0
                : 0.0;

        out.println("# HELP app_requests_total Total number of requests handled by this instance");
        out.println("# TYPE app_requests_total counter");
        out.println("app_requests_total{instance=\"" + INSTANCE + "\"} " + requestCount);

        out.println("# HELP app_db_errors_total Total number of database errors encountered");
        out.println("# TYPE app_db_errors_total counter");
        out.println("app_db_errors_total{instance=\"" + INSTANCE + "\"} " + dbErrorCount);

        out.println("# HELP app_response_time_seconds_avg Average response time in seconds");
        out.println("# TYPE app_response_time_seconds_avg gauge");
        out.println("app_response_time_seconds_avg{instance=\"" + INSTANCE + "\"} " + avgResponseSeconds);

        out.println("# HELP app_up Whether this app instance is up (always 1 if reachable)");
        out.println("# TYPE app_up gauge");
        out.println("app_up{instance=\"" + INSTANCE + "\"} 1");
    }
}
