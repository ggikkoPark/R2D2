package ggikko.me.r2d2.subway;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.data.SubwayInformation;
import ggikko.me.r2d2.util.ResultCodeCollections;

/**
 * Created by ggikko on 15. 12. 30..
 */
public class SubwayAdapter extends ExpandableRecyclerAdapter<SubwayAdapter.LocationListItem> {

    public static final int TYPE_PERSON = 1001;
    List<LocationListItem> items = new ArrayList<>();

    public SubwayAdapter(Context context) {
        super(context);

        setItems(getSampleItems());
    }

    public static class LocationListItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text;
        public String fullText;
        public int position;
        public int type;

        public LocationListItem(String group) {
            super(TYPE_HEADER);
            this.type = TYPE_HEADER;
            Text = group;
        }

        public LocationListItem(String first, String last, String fullText, int position) {
            super(TYPE_PERSON);
            this.type = TYPE_PERSON;
            Text = first + " " + last;
            this.fullText = fullText;
            this.position = position;
        }

    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;

        public HeaderViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_header_name);
        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
        }

    }

    public class SubwayViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name;

        public SubwayViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fullText = name.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("subway", fullText);

                    ((SubwayActivty)mContext).setResult(0 , intent);;
                    ((SubwayActivty)mContext).finish();
                }
            });

        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_header, parent));
            case TYPE_PERSON:
            default:
                return new SubwayViewHolder(inflate(R.layout.item_subway, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((SubwayViewHolder) holder).bind(position);
                break;
        }
    }

    //su, bu, dg, dj, us, gj, ic, gw, gg, cb, cn, kb, kn, jb
    private List<LocationListItem> getSampleItems() {
        items = new ArrayList<>();

        SubwayInformation subwayInformation = new SubwayInformation();
        int size = subwayInformation.location.size();
        List<List<String>> allLocation = subwayInformation.getAllLocation();

        for (int i = 0; i < size; i++) {
            items.add(new LocationListItem(subwayInformation.location.get(i)));
            List<String> tempList = allLocation.get(i);
            int size1 = tempList.size();

            for (int j = 0; j < size1; j++) {
                String tempLocation = tempList.get(j);
                String[] arrays = tempLocation.split(" ");
                items.add(new LocationListItem("", arrays[1], tempLocation, i+j));
            }
        }

        return items;
    }
}
