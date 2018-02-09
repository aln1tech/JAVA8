import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class date {

    public static void main(String[] args) throws ParseException {
        Date originalFrom = formatDate("yyyy-MM-dd", "2017-03-01");
        Date originalTo = formatDate("yyyy-MM-dd", "2018-06-30");

        List<ReportDump> reportDumpList = new ArrayList<>();
        reportDumpList.add(new ReportDump(formatDate("yyyy-MM-dd", "2016-06-01"),
                formatDate("yyyy-MM-dd", "2018-03-15"), "Task 1"));
        reportDumpList.add(new ReportDump(formatDate("yyyy-MM-dd", "2016-02-01"),
                formatDate("yyyy-MM-dd", "2017-06-30"), "Task 2"));
        List<ReportDump> finalReportDumpList = new ArrayList<>();
        for (ReportDump teamMember : reportDumpList) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(teamMember.getFromDate());
            while (cal.getTime().before(teamMember.getToDate())
                    || cal.getTime().equals(teamMember.getToDate())) {
                ReportDump plannedHr = new ReportDump();
                plannedHr.setReportedDate(cal.getTime());
                plannedHr.setTask(teamMember.getTask());
                finalReportDumpList.add(plannedHr);
                cal.add(Calendar.DATE, 1);
            }
        }

        finalReportDumpList.forEach(System.out::println);
    }

    private static Date formatDate(String formatter, String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        return simpleDateFormat.parse(dateString);
    }
}

class ReportDump {
    private Date fromDate;
    private Date toDate;
    private String task;
    private Date reportedDate;

    public ReportDump() {
    }

    public ReportDump(final Date fromDate, final Date toDate, final String task) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.task = task;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public String getTask() {
        return task;
    }

    public void setTask(final String task) {
        this.task = task;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(final Date reportedDate) {
        this.reportedDate = reportedDate;
    }


    @Override
    public String toString() {
        return "ReportDump{" +
                "task='" + task + '\'' +
                ", reportedDate=" + reportedDate +
                '}';
    }
}
