package permoveo.com.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import permoveo.com.library.R;

/**
 * Created by byfieldj on 2/1/17.
 */

public class LocateMeView extends RelativeLayout {

    private LocationListener mListener;

    public interface LocationListener {

        void onLocateButtonSelected();
        void onLocationError();
    }


    public LocateMeView(Context context) {
        super(context);
        initialize();
    }

    public LocateMeView(Context context, AttributeSet set) {
        super(context, set);
        initialize();
    }

    public LocateMeView(Context context, AttributeSet set, int defStyle){
        super(context, set, defStyle);
        initialize();
    }

    public void setListener(LocationListener listener){
        this.mListener = listener;
    }

    public void initialize(){
        inflate(getContext(), R.layout.view_locate_me, this);

        Button locateMeButton =  (Button) findViewById(R.id.b_location);
        locateMeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onLocateButtonSelected();
            }
        });
    }
}
