package ggikko.me.r2d2.spinner;

/**
 * Created by ggikko on 16. 1. 2..
 */

import android.content.Context;
import android.widget.ListAdapter;

/**
 * @author angelo.marchesin
 * 이 소스는 GITHUB저장소 https://github.com/arcadefire/nice-spinner 에서 가져왔습니다.
 * 라이센스 : Apache v2.0
 * 이 클래스는 스피너를 꾸며줄 커스터마이징 된 스피너 어답터 클래스 입니다.
 */

public class NiceSpinnerAdapterWrapper extends NiceSpinnerBaseAdapter {

    private final ListAdapter mBaseAdapter;

    public NiceSpinnerAdapterWrapper(Context context, ListAdapter toWrap) {
        super(context);
        mBaseAdapter = toWrap;
    }

    @Override
    public int getCount() {
        return mBaseAdapter.getCount() - 1;
    }

    @Override
    public Object getItem(int position) {
        if (position >= mSelectedIndex) {
            return mBaseAdapter.getItem(position + 1);
        } else {
            return mBaseAdapter.getItem(position);
        }
    }

    @Override
    public Object getItemInDataset(int position) {
        return mBaseAdapter.getItem(position);
    }
}