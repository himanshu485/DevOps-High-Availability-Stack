package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.*;

@WebServlet("/")
public class HelloServlet extends HttpServlet {

    private static final String INSTANCE =
            System.getenv().getOrDefault(
                    "INSTANCE_NAME",
                    getHostname());

    private static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        long startTime = System.currentTimeMillis();

        int count = 0;
        boolean dbOk = true;

        try (Connection conn = DBUtil.getConnection()) {

            Statement st = conn.createStatement();

            st.execute(
                    "CREATE TABLE IF NOT EXISTS visits(" +
                    "id SERIAL PRIMARY KEY)");

            st.execute("INSERT INTO visits DEFAULT VALUES");

            ResultSet rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM visits");

            if (rs.next())
                count = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
            dbOk = false;
            AppMetrics.recordDbError();
        }

        long durationMs = System.currentTimeMillis() - startTime;

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        String hostname = INSTANCE;
        String now = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        String responseTime = durationMs + "ms";

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>DevOps Dashboard</title>");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap\" rel=\"stylesheet\">");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body {");
        out.println("  font-family: 'Inter', -apple-system, sans-serif;");
        out.println("  min-height: 100vh;");
        out.println("  background: #0f0f1a;");
        out.println("  background-image: radial-gradient(ellipse at 20% 50%, rgba(99, 102, 241, 0.08) 0%, transparent 50%),");
        out.println("                    radial-gradient(ellipse at 80% 50%, rgba(139, 92, 246, 0.08) 0%, transparent 50%);");
        out.println("  display: flex;");
        out.println("  justify-content: center;");
        out.println("  align-items: center;");
        out.println("  padding: 2rem;");
        out.println("}");
        out.println(".container {");
        out.println("  max-width: 1100px;");
        out.println("  width: 100%;");
        out.println("}");
        out.println(".header {");
        out.println("  text-align: center;");
        out.println("  margin-bottom: 3rem;");
        out.println("}");
        out.println(".header h1 {");
        out.println("  font-size: 2.5rem;");
        out.println("  font-weight: 700;");
        out.println("  background: linear-gradient(135deg, #818cf8, #c084fc);");
        out.println("  -webkit-background-clip: text;");
        out.println("  -webkit-text-fill-color: transparent;");
        out.println("  background-clip: text;");
        out.println("}");
        out.println(".header p {");
        out.println("  color: #71717a;");
        out.println("  margin-top: 0.5rem;");
        out.println("  font-size: 1.1rem;");
        out.println("}");
        out.println(".grid {");
        out.println("  display: grid;");
        out.println("  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));");
        out.println("  gap: 1.5rem;");
        out.println("  margin-bottom: 2rem;");
        out.println("}");
        out.println(".card {");
        out.println("  background: rgba(255, 255, 255, 0.03);");
        out.println("  border: 1px solid rgba(255, 255, 255, 0.06);");
        out.println("  border-radius: 16px;");
        out.println("  padding: 1.75rem;");
        out.println("  backdrop-filter: blur(12px);");
        out.println("  transition: transform 0.2s ease, border-color 0.2s ease;");
        out.println("}");
        out.println(".card:hover {");
        out.println("  transform: translateY(-2px);");
        out.println("  border-color: rgba(129, 140, 248, 0.3);");
        out.println("}");
        out.println(".card-label {");
        out.println("  font-size: 0.75rem;");
        out.println("  font-weight: 600;");
        out.println("  text-transform: uppercase;");
        out.println("  letter-spacing: 0.08em;");
        out.println("  color: #52525b;");
        out.println("  margin-bottom: 0.5rem;");
        out.println("}");
        out.println(".card-value {");
        out.println("  font-size: 2rem;");
        out.println("  font-weight: 700;");
        out.println("  color: #f4f4f5;");
        out.println("}");
        out.println(".card-sub {");
        out.println("  font-size: 0.85rem;");
        out.println("  color: #71717a;");
        out.println("  margin-top: 0.25rem;");
        out.println("}");
        out.println(".card-icon {");
        out.println("  display: inline-flex;");
        out.println("  align-items: center;");
        out.println("  justify-content: center;");
        out.println("  width: 40px;");
        out.println("  height: 40px;");
        out.println("  border-radius: 10px;");
        out.println("  margin-bottom: 1rem;");
        out.println("  font-size: 1.2rem;");
        out.println("}");
        out.println(".status-bar {");
        out.println("  margin-top: 2rem;");
        out.println("  background: rgba(255, 255, 255, 0.03);");
        out.println("  border: 1px solid rgba(255, 255, 255, 0.06);");
        out.println("  border-radius: 16px;");
        out.println("  padding: 1rem 1.75rem;");
        out.println("  display: flex;");
        out.println("  align-items: center;");
        out.println("  justify-content: space-between;");
        out.println("  flex-wrap: wrap;");
        out.println("  gap: 1rem;");
        out.println("}");
        out.println(".status-item {");
        out.println("  display: flex;");
        out.println("  align-items: center;");
        out.println("  gap: 0.75rem;");
        out.println("  font-size: 0.9rem;");
        out.println("  color: #a1a1aa;");
        out.println("}");
        out.println(".dot {");
        out.println("  width: 8px;");
        out.println("  height: 8px;");
        out.println("  border-radius: 50%;");
        out.println("  display: inline-block;");
        out.println("}");
        out.println(".dot-green { background: #22c55e; box-shadow: 0 0 8px rgba(34,197,94,0.4); }");
        out.println(".dot-red { background: #ef4444; box-shadow: 0 0 8px rgba(239,68,68,0.4); }");
        out.println(".glow-green { color: #22c55e; }");
        out.println(".glow-red { color: #ef4444; }");
        out.println(".footer {");
        out.println("  text-align: center;");
        out.println("  margin-top: 2rem;");
        out.println("  font-size: 0.8rem;");
        out.println("  color: #52525b;");
        out.println("}");
        out.println("@media (max-width: 600px) {");
        out.println("  .header h1 { font-size: 1.75rem; }");
        out.println("  .card-value { font-size: 1.5rem; }");
        out.println("  body { padding: 1rem; }");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");

        out.println("  <div class=\"header\">");
        out.println("    <h1>DevOps Dashboard</h1>");
        out.println("    <p>High-Availability Stack Status</p>");
        out.println("  </div>");

        out.println("  <div class=\"grid\">");

        out.println("    <div class=\"card\">");
        out.println("      <div class=\"card-icon\" style=\"background: rgba(99,102,241,0.15); color: #818cf8;\">");
        out.println("        <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><rect x=\"2\" y=\"3\" width=\"20\" height=\"14\" rx=\"2\" ry=\"2\"/><line x1=\"8\" y1=\"21\" x2=\"16\" y2=\"21\"/><line x1=\"12\" y1=\"17\" x2=\"12\" y2=\"21\"/></svg>");
        out.println("      </div>");
        out.println("      <div class=\"card-label\">Server</div>");
        out.println("      <div class=\"card-value\">" + hostname + "</div>");
        out.println("      <div class=\"card-sub\">Active instance</div>");
        out.println("    </div>");

        out.println("    <div class=\"card\">");
        out.println("      <div class=\"card-icon\" style=\"background: rgba(34,197,94,0.15); color: #22c55e;\">");
        out.println("        <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M22 12h-4l-3 9L9 3l-3 9H2\"/></svg>");
        out.println("      </div>");
        out.println("      <div class=\"card-label\">Visitor Count</div>");
        out.println("      <div class=\"card-value\">" + count + "</div>");
        out.println("      <div class=\"card-sub\">Total visits recorded</div>");
        out.println("    </div>");

        out.println("    <div class=\"card\">");
        out.println("      <div class=\"card-icon\" style=\"background: rgba(251,191,36,0.15); color: #fbbf24;\">");
        out.println("        <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><circle cx=\"12\" cy=\"12\" r=\"10\"/><polyline points=\"12 6 12 12 16 14\"/></svg>");
        out.println("      </div>");
        out.println("      <div class=\"card-label\">Response Time</div>");
        out.println("      <div class=\"card-value\">" + responseTime + "</div>");
        out.println("      <div class=\"card-sub\">Current request</div>");
        out.println("    </div>");

        out.println("    <div class=\"card\">");
        out.println("      <div class=\"card-icon\" style=\"background: rgba(236,72,153,0.15); color: #ec4899;\">");
        out.println("        <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><ellipse cx=\"12\" cy=\"5\" rx=\"9\" ry=\"3\"/><path d=\"M21 12c0 1.66-4 3-9 3s-9-1.34-9-3\"/><path d=\"M3 5v14c0 1.66 4 3 9 3s9-1.34 9-3V5\"/></svg>");
        out.println("      </div>");
        out.println("      <div class=\"card-label\">Database</div>");
        out.println("      <div class=\"card-value\">" + (dbOk ? "Online" : "Error") + "</div>");
        out.println("      <div class=\"card-sub\" style=\"color: " + (dbOk ? "#22c55e" : "#ef4444") + ";\">" + (dbOk ? "Postgres connected" : "Connection failed") + "</div>");
        out.println("    </div>");

        out.println("  </div>");

        out.println("  <div class=\"status-bar\">");
        out.println("    <div class=\"status-item\">");
        out.println("      <span class=\"dot dot-green\"></span>");
        out.println("      System Healthy");
        out.println("    </div>");
        out.println("    <div class=\"status-item\">");
        out.println("      <svg width=\"14\" height=\"14\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"#71717a\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><circle cx=\"12\" cy=\"12\" r=\"10\"/><polyline points=\"12 6 12 12 16 14\"/></svg>");
        out.println("      " + now);
        out.println("    </div>");
        out.println("    <div class=\"status-item\">");
        out.println("      <svg width=\"14\" height=\"14\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"#71717a\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"/><circle cx=\"12\" cy=\"7\" r=\"4\"/></svg>");
        out.println("      " + hostname);
        out.println("    </div>");
        out.println("  </div>");

        out.println("  <div class=\"footer\">");
        out.println("    HA Stack &middot; Tomcat &middot; Postgres &middot; HAProxy &middot; ELK &middot; Prometheus");
        out.println("  </div>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

        AppMetrics.recordRequest(durationMs);
    }
}
