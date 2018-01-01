package senanayake.udayanga.com.bustime;

/**
 * Created by Udayanga on 12/10/2017.
 */

public class Route {
    int id;
    String placeAdded;
    String from;
    String to;
    int routeNo;
    String time;

    public Route() {
    }

    public Route(int id, String placeAdded, String from, String to, int routeNo, String time) {
        this.id = id;
        this.placeAdded = placeAdded;
        this.from = from;
        this.to = to;
        this.routeNo = routeNo;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceAdded() {
        return placeAdded;
    }

    public void setPlaceAdded(String placeAdded) {
        this.placeAdded = placeAdded;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", placeAdded='" + placeAdded + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", routeNo=" + routeNo +
                ", time='" + time + '\'' +
                '}';
    }
}
