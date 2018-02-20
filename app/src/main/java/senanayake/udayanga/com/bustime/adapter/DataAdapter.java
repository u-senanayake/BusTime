package senanayake.udayanga.com.bustime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import senanayake.udayanga.com.bustime.R;
import senanayake.udayanga.com.bustime.model.Route;

/**
 * Created by Udayanga on 1/26/2018.
 */

public class DataAdapter extends BaseAdapter {
    private ArrayList<Route> routes;
    private LayoutInflater layoutInflater;
    private Context context;

    public DataAdapter(Context context, ArrayList<Route> routes) {
        this.routes = routes;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return routes.size();
    }

    @Override
    public Object getItem(int position) {
        return routes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.bus_list, null);
            holder = new ViewHolder();
            holder.txtPlace = convertView.findViewById(R.id.txtPlace);
            holder.txtFrom = convertView.findViewById(R.id.txtFrom);
            holder.txtTo = convertView.findViewById(R.id.txtTo);
            holder.txtRoute = convertView.findViewById(R.id.txtRoute);
            holder.txtTime = convertView.findViewById(R.id.txtTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtPlace.setText(routes.get(position).getPlaceAdded());
        holder.txtFrom.setText(routes.get(position).getFrom());
        holder.txtTo.setText(routes.get(position).getTo());
        holder.txtRoute.setText(Integer.toString(routes.get(position).getRouteNo()));
        holder.txtTime.setText(routes.get(position).getTime());

        return convertView;
    }

    static class ViewHolder {
        TextView txtPlace;
        TextView txtFrom;
        TextView txtTo;
        TextView txtRoute;
        TextView txtTime;
    }

}
