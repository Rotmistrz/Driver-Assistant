package pl.filipmarkiewicz.asystentkierowcy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Filip on 2018-03-19.
 */

public class SimpleRecordAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private Context ctx;

    private SimpleRecord[] records;

    public SimpleRecordAdapter(Context c, SimpleRecord[] records) {
        super();

        this.records = records;

        this.ctx = c;
    }

    public int getCount() {
        return this.records.length;
    }

    public Object getItem(int position) {
        return this.records[position];
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View mV;

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.simple_record_row, null);
        }

        mV = view;

        SimpleRecord current = records[position];

        TextView code = (TextView) mV.findViewById(R.id.code);
        code.setText(current.getCode());

        TextView key = (TextView) mV.findViewById(R.id.key);
        key.setText(current.getKey());

        TextView value = (TextView) mV.findViewById(R.id.value);
        value.setText(current.getValue());

        return mV;
    }
}