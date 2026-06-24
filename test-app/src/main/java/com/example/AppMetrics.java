package com.example;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple in-memory metrics holder.
 * Tracks request counts and basic timing, exposed later via MetricsServlet
 * in Prometheus text exposition format.
 */
public class AppMetrics {

    public static final AtomicLong REQUEST_COUNT = new AtomicLong(0);
    public static final AtomicLong DB_ERROR_COUNT = new AtomicLong(0);
    public static final AtomicLong TOTAL_RESPONSE_TIME_MS = new AtomicLong(0);

    private AppMetrics() {
        // utility class, no instances
    }

    public static void recordRequest(long durationMs) {
        REQUEST_COUNT.incrementAndGet();
        TOTAL_RESPONSE_TIME_MS.addAndGet(durationMs);
    }

    public static void recordDbError() {
        DB_ERROR_COUNT.incrementAndGet();
    }
}
