package ggikko.me.r2d2.home;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ggikko.me.r2d2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {


    private static String TAG = "RestaurantListFragment";

    private RecyclerView recyclerView;

    /**
     * Empty Constructor
     */
    public RestaurantListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_restaurant_list);
        setupRecyclerView(recyclerView);

        return rootView;
    }

    /**
     *
     * @param upRecyclerView
     */
    public void setupRecyclerView(RecyclerView upRecyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        String[] testdata =
                        {"1. 맥도날드", "2. 피자훗", "3. 코딩매니아","4. 롯데리드",
                        "5. KFG" , "6. 네이바", "7. 오빠닭쵸" , "8. 버거퀸",
                                "9. 넥슬라이스" ,"10. 구글", "6. 네이바", "7. 오빠닭쵸" , "8. 버거퀸" };

        recyclerView.setAdapter(new ResultRecyclerViewAdapter(getActivity(), testdata));
    }

    public static class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder>{

        private String[] mValues;
        private Context mContext;

        public ResultRecyclerViewAdapter(Context context, String[] data) {
            this.mContext = context;
            this.mValues = data;
        }

        @Override
        public ResultRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ResultRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mTextview.setText(mValues[position]);
            holder.mView.setOnClickListener(v -> Snackbar.make(v, getValueAt(position), Snackbar.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return mValues.length;
        }

        public String getValueAt(int position){
            return mValues[position];
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView mTextview;

            public ViewHolder(View itemView) {
                super(itemView);
                this.mView = itemView;
                this.mTextview = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }


}
