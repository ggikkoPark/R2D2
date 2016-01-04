package ggikko.me.r2d2.home;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.domain.RestaurantDto;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {


    private static String TAG = "RestaurantListFragment";

    private RecyclerView recyclerView;

    /**
     * RetrofitInstance 인스턴스
     */
    private static RestaurantListFragment instance;

    /**
     * Empty Constructor
     */
    public RestaurantListFragment() {
    }

    /**
     * 싱글톤 패턴을 이용하여 인스턴스 생성
     */
    public static RestaurantListFragment getInstance() {
        if (instance == null) {
            instance = new RestaurantListFragment();
        }
        return instance;
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
     * @param upRecyclerView
     */
    public void setupRecyclerView(RecyclerView upRecyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        String[] testdata = {"1.", "2.", "3.", "4.", "5.", "6.", "7.", "8.", "9.", "10."};
        recyclerView.setAdapter(new ResultRecyclerViewAdapter(getActivity(), testdata));
    }

    /**
     * 데이터를 받아서 리스트를 바꿔준다
     *
     * @param body
     */
    public void changeListData(RestaurantDto.GetRestaurantsResponse body) {

        /** 테스트 중 */
        String restaurant1 = body.getRestaurant1();
        String restaurant2 = body.getRestaurant2();
        String restaurant3 = body.getRestaurant3();
        String restaurant4 = body.getRestaurant4();
        String restaurant5 = body.getRestaurant5();
        String restaurant6 = body.getRestaurant6();
        String restaurant7 = body.getRestaurant7();
        String restaurant8 = body.getRestaurant8();
        String restaurant9 = body.getRestaurant9();
        String restaurant10 = body.getRestaurant10();

        Log.e("ggikko", restaurant1 + "1");
        Log.e("ggikko", restaurant2 + "2");
        Log.e("ggikko", restaurant3 + "3");
        Log.e("ggikko", restaurant4 + "4");
        Log.e("ggikko", restaurant5 + "5");
        Log.e("ggikko", restaurant6 + "6");
        Log.e("ggikko", restaurant7 + "7");
        Log.e("ggikko", restaurant8 + "8");
        Log.e("ggikko", restaurant9 + "9");
        Log.e("ggikko", restaurant10 + "10");

        String[] testdata = {restaurant1,restaurant2,restaurant3,restaurant4,restaurant5,restaurant6,restaurant7
        ,restaurant8,restaurant9,restaurant10};

        recyclerView.setAdapter(new ResultRecyclerViewAdapter(getActivity(), testdata));

    }

    public static class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder> {

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

        public String getValueAt(int position) {
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
