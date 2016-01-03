package ggikko.me.r2d2.spinner;

/**
 * Created by ggikko on 16. 1. 2..
 */
import android.content.Context;
import java.util.List;

/**
 * @author angelo.marchesin
 * 이 소스는 GITHUB저장소 https://github.com/arcadefire/nice-spinner 에서 가져왔습니다.
 * 라이센스 : Apache v2.0
 * 이 클래스는 스피너를 꾸며줄 커스터마이징 된 스피너 어답터 클래스 입니다.
 */
public class NiceSpinnerAdapter<T> extends NiceSpinnerBaseAdapter {

    private final List<T> mItems;

    public NiceSpinnerAdapter(Context context, List<T> items) {
        super(context);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size() - 1;
    }

    @Override
    public T getItem(int position) {
        if (position >= mSelectedIndex) {
            return mItems.get(position + 1);
        } else {
            return mItems.get(position);
        }
    }

    @Override
    public T getItemInDataset(int position) {
        return mItems.get(position);
    }
}