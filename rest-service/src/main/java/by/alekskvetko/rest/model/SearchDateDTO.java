package by.alekskvetko.rest.model;

/**
 * Data transfer object for passing data between layers
 * It contains 3 parameters used in search queries for employee entity:
 *
 *      startDate - start of a period of time for search
 *      endDate - end of a period of time for search
 *      certainDate - certain date for search
 *
 * @author ALEKSANDR KVETKO
 */
public class SearchDateDTO {

    private String startDate;
    private String endDate;
    private String certainDate;

    public SearchDateDTO(String startDate, String endDate, String certainDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.certainDate = certainDate;
    }

    public SearchDateDTO() {
    }

    public String getStartDate() {

        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCertainDate() {
        return certainDate;
    }

    public void setCertainDate(String certainDate) {
        this.certainDate = certainDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchDateDTO that = (SearchDateDTO) o;

        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        return certainDate != null ? certainDate.equals(that.certainDate) : that.certainDate == null;

    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (certainDate != null ? certainDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchDateDTO{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", certainDate='" + certainDate + '\'' +
                '}';
    }
}
