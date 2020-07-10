package com.software4bikers.motorcyclerun.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.software4bikers.motorcyclerun.models.SessionsData;

import com.software4bikers.motorcyclerun.R;


import java.util.ArrayList;
import java.util.List;

public class SessionsDataAdapter extends ArrayAdapter<SessionsData> {

    private List<SessionsData> sessionList = new ArrayList<SessionsData>();

    public SessionsDataAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class SessionDataViewHolder {
        TextView id;
        TextView createdAt;
    }

    @Override
    public void add(@Nullable SessionsData object) {
        sessionList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.sessionList.size();
    }

    @Nullable
    @Override
    public SessionsData getItem(int index) {
        return this.sessionList.get(index);
    }
    @NonNull

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        SessionsDataAdapter.SessionDataViewHolder viewHolder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_sessions, parent, false);
            viewHolder = new SessionsDataAdapter.SessionDataViewHolder();
            viewHolder.id = row.findViewById(R.id.id);
            viewHolder.createdAt = row.findViewById(R.id.createdAt);
            row.setTag(viewHolder);
        } else {
            viewHolder = (SessionsDataAdapter.SessionDataViewHolder)row.getTag();
        }
        SessionsData order = getItem(position);
        viewHolder.id.setText(order.getId());
        viewHolder.createdAt.setText(order.getCreatedAt());

        return row;

    }

}
