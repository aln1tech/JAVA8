package demo.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BotMergeOpertion {
    public static void main(String[] args) throws ParseException {
        List<ReportDump> plannedHrReportDumpList = getPlannedHrReportDumpList();
        List<ReportDump> actualHrReportDumpList = getActualHrReportDumpList();

        System.out.print("Planned Reportdump :");
        plannedHrReportDumpList.forEach(reportDump -> System.out.println(reportDump.toString()));

        System.out.println();

        System.out.print("Actual Reportdump :");
        actualHrReportDumpList.forEach(reportDump -> System.out.println(reportDump.toString()));

        System.out.println(" ********** Iterating in Old Format ****************");

        List<ReportDump> finalReportDump = mergePannedAndActualReportDumpUsingOldFormat(plannedHrReportDumpList, actualHrReportDumpList);

        System.out.println();

        System.out.print("Result Reportdump :");
        finalReportDump.forEach(reportDump -> System.out.println(reportDump.toString()));

        System.out.println(" ********** Iterating in New Format ****************");

        finalReportDump = mergePannedAndActualReportDump(plannedHrReportDumpList, actualHrReportDumpList);

        System.out.println();

        System.out.print("Result Reportdump :");
        finalReportDump.forEach(reportDump -> System.out.println(reportDump.toString()));


    }

    /*
    *  Need to merge planned and actual dump based on issueid , reportdate , userid and projectname.
    *
    *  steps to process
    *  ===============
    *  1. take planned reportdump and iterate it
    *  2. first record of planned report dump will compare with acutal reportdump list one by one.
    *  3. if the planned reportdump record matches with actual dump
    *  4. then need to merge records by adding updated record in final report and stop comparing and remove actual dump record.
     * 5. else part - actual reportdump not match - add planned dump into final report list.
    * */
    private static List<ReportDump> mergePannedAndActualReportDump(final List<ReportDump> plannedHrReportDumpList, final List<ReportDump> actualHrReportDumpList) {

        BiConsumer<ReportDump, ReportDump> biConsumer = (x, y) -> System.out.println(x.merge(y));
        Consumer<ReportDump> consumer = (x) -> System.out.println(x);

        List<ReportDump> finalList = new ArrayList<>();

        Consumer<ReportDump> finalListConsumer = (x) -> {
            System.out.println(x);
            finalList.add(x);
        };


        List<ReportDump> reportDumpList = plannedHrReportDumpList.stream()
                .filter(plannedHrReportDump ->
                                actualHrReportDumpList
                                        .removeIf(actualHrReportDump -> {
                                            boolean check = plannedHrReportDump.equals(actualHrReportDump);
                                            //System.out.println("Check - " + check + " - P-ID :" + plannedHrReportDump.getId() + " - A-ID:" + actualHrReportDump.getId());
                                            if (check) {
                                                plannedHrReportDump.merge(actualHrReportDump);
                                            }
                                            return check;
                                        })
                                /*.stream()
                                .anyMatch(
                                        actualHrReportDump -> {
                                            boolean check = plannedHrReportDump.equals(actualHrReportDump);
                                            //System.out.println("Check - " + check + " - P-ID :" + plannedHrReportDump.getId() + " - A-ID:" + actualHrReportDump.getId());
                                            return check;
                                        }
                                )*/
                )
                .collect(Collectors.toList());
        reportDumpList.addAll(actualHrReportDumpList);

        return reportDumpList;
    }

    private static List<ReportDump> mergePannedAndActualReportDumpUsingOldFormat(final List<ReportDump> plannedHrReportDumpList, final List<ReportDump> actualHrReportDumpList) {
        List<ReportDump> finalList = new ArrayList<>();
        if (plannedHrReportDumpList != null) {
            Iterator<ReportDump> pIterator = plannedHrReportDumpList.iterator();
            while (pIterator.hasNext()) {
                boolean found = false;
                ReportDump pObject = pIterator.next();
                Iterator<ReportDump> aIterator = actualHrReportDumpList.iterator();
                while (aIterator.hasNext()) {
                    ReportDump aObject = aIterator.next();
                    if (pObject.equals(aObject)) {
                        found = true;
                        ReportDump merged = pObject.merge(aObject);
                        finalList.add(merged);
                        aIterator.remove();
                        break;
                    }
                }
                if (found) {
                    pIterator.remove();
                }
            }
            finalList.addAll(actualHrReportDumpList);
            finalList.addAll(plannedHrReportDumpList);
        }
        return finalList;
    }

    private static List<ReportDump> getPlannedHrReportDumpList() throws ParseException {
        List<ReportDump> plannedHrReportDumpList = new ArrayList<>();
        plannedHrReportDumpList.add(new ReportDump(1l, "12345", "lokesh", "K.12345:Maths",
                "K.12345:Maths_Task", formatDate("yyyy-MM-dd", "2017-01-01"),
                getPlannedHrForGivenPercent(15), 0));
        plannedHrReportDumpList.add(new ReportDump(2l, "67890", "suresh", "K.67890:PT",
                "K.67890:PT_Task", formatDate("yyyy-MM-dd", "2017-01-01"),
                getPlannedHrForGivenPercent(30), 0));
        plannedHrReportDumpList.add(new ReportDump(3l, "67890", "suresh", "K.67890:PT",
                "K.67890:PT_Task", formatDate("yyyy-MM-dd", "2017-01-02"),
                getPlannedHrForGivenPercent(30), 0));
        plannedHrReportDumpList.add(new ReportDump(4l, "67890", "suresh", "K.67890:PT",
                "K.67890:PT_Task", formatDate("yyyy-MM-dd", "2017-01-03"),
                getPlannedHrForGivenPercent(30), 0));
        plannedHrReportDumpList.add(new ReportDump(5l, "12345", "lokesh", "K.12345:Maths",
                "K.12345:Maths_Task", formatDate("yyyy-MM-dd", "2017-01-02"),
                getPlannedHrForGivenPercent(15), 0));
        plannedHrReportDumpList.add(new ReportDump(6l, "12345", "lokesh", "K.12345:Maths",
                "K.12345:Maths_Task", formatDate("yyyy-MM-dd", "2017-01-03"),
                getPlannedHrForGivenPercent(15), 0));
        return plannedHrReportDumpList;
    }


    private static List<ReportDump> getActualHrReportDumpList() throws ParseException {
        List<ReportDump> actualHrReportDumpList = new ArrayList<>();
        actualHrReportDumpList.add(new ReportDump(1l, "12345", "lokesh", "K.12345:Maths",
                "K.12345:Maths_Task", formatDate("yyyy-MM-dd", "2017-01-01"), 0,
                getPlannedHrForGivenPercent(15)));
        actualHrReportDumpList.add(new ReportDump(2l, "67890", "suresh", "K.67890:PT",
                "K.67890:PT_Task", formatDate("yyyy-MM-dd", "2017-01-01"), 0,
                getPlannedHrForGivenPercent(30)));
        actualHrReportDumpList.add(new ReportDump(3l, "67890", "suresh", "K.67890:PT",
                "K.67890:PT_Task", formatDate("yyyy-MM-dd", "2017-01-02"), 0,
                getPlannedHrForGivenPercent(30)));
        actualHrReportDumpList.add(new ReportDump(4l, "67890", "suresh", "K.67890:PT",
                "K.67890:PT_Task", formatDate("yyyy-MM-dd", "2017-01-03"), 0,
                getPlannedHrForGivenPercent(30)));
        actualHrReportDumpList.add(new ReportDump(5l, "12345", "lokesh", "K.12345:Maths",
                "K.12345:Maths_Task", formatDate("yyyy-MM-dd", "2017-01-02"), 0,
                getPlannedHrForGivenPercent(15)));
        return actualHrReportDumpList;
    }

    private static double getPlannedHrForGivenPercent(final int percent) {
        return (percent * 8) / 100;
    }

    private static Date formatDate(String formatter, String dateString) throws ParseException {
        return new SimpleDateFormat(formatter).parse(dateString);
    }
}

class ReportDump {
    private long id;
    private String issueId;
    private String user;
    private String projectName;
    private String taskName;
    private Date reportDate;
    private double plannedHr;
    private double actualHr;

    public ReportDump() {
    }

    public ReportDump(final long id, final String issueId, final String user, final String projectName, final String taskName, final Date reportDate, final double plannedHr, final double actualHr) {
        this.id = id;
        this.issueId = issueId;
        this.user = user;
        this.projectName = projectName;
        this.taskName = taskName;
        this.reportDate = reportDate;
        this.plannedHr = plannedHr;
        this.actualHr = actualHr;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(final String issueId) {
        this.issueId = issueId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(final Date reportDate) {
        this.reportDate = reportDate;
    }

    public double getPlannedHr() {
        return plannedHr;
    }

    public void setPlannedHr(final double plannedHr) {
        this.plannedHr = plannedHr;
    }

    public double getActualHr() {
        return actualHr;
    }

    public void setActualHr(final double actualHr) {
        this.actualHr = actualHr;
    }

    @Override
    public String toString() {
        return "ReportDump{" +
                "id=" + id +
                ", issueId='" + issueId + '\'' +
                ", user='" + user + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", reportDate=" + reportDate +
                ", plannedHr=" + plannedHr +
                ", actualHr=" + actualHr +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ReportDump that = (ReportDump) o;

        if (issueId != null ? !issueId.equals(that.issueId) : that.issueId != null) {
            return false;
        }
        if (user != null ? !user.equals(that.user) : that.user != null) {
            return false;
        }
        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) {
            return false;
        }
        return reportDate != null ? reportDate.equals(that.reportDate) : that.reportDate == null;
    }

    @Override
    public int hashCode() {
        int result = issueId != null ? issueId.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (reportDate != null ? reportDate.hashCode() : 0);
        return result;
    }

    public ReportDump merge(final Object o) {
        final ReportDump that = (ReportDump) o;
        actualHr = that.getActualHr();
        return this;
    }
}
