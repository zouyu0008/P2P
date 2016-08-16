package pres.sample.yu.p2p.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import pres.sample.yu.p2p.R;
import pres.sample.yu.p2p.utils.UIUtils;

/**
 * Created by yu on 2016/8/15.
 */
public abstract class loadingpage extends FrameLayout{
    public static final int PAGE_STATE_LOADING=1;
    public static final int PAGE_STATE_ERROR=2;
    public static final int PAGE_STATE_EMPTY=3;
    public static final int PAGE_STATE_SUCCESS=4;
    private final Context mContext;
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;
    private LayoutParams params;
    private int page_state_current=1;

    public loadingpage(Context context) {
        this(context,null);
    }

    public loadingpage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public loadingpage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        //初始化四个界面
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(loadingView==null) {
            loadingView = UIUtils.getXmlView(R.layout.page_loading);
            addView(loadingView, params);
        }
        if(emptyView==null) {
            emptyView = UIUtils.getXmlView(R.layout.page_empty);
            addView(emptyView, params);
        }
        if(errorView==null) {
            errorView = UIUtils.getXmlView(R.layout.page_error);
            addView(errorView, params);
        }
        showSafePage();
    }

    private void showSafePage() {
        //保证在主线程运行
        UIUtils.onRunUiThread(new Runnable() {
            @Override
            public void run() {
                showpage();
            }
        });
    }
    private void showpage() {
        //根据状态展示相对状态
        loadingView.setVisibility(page_state_current == PAGE_STATE_LOADING ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(page_state_current == PAGE_STATE_EMPTY ? View.VISIBLE : View.GONE);
        errorView.setVisibility(page_state_current == PAGE_STATE_ERROR ? View.VISIBLE : View.GONE);
        if(successView == null) {
//            successView = UIUtils.getXmlView(layoutId());
            successView = View.inflate(mContext,layoutId(),null);
            addView(successView,params);
        }
        successView.setVisibility(page_state_current == PAGE_STATE_SUCCESS ? View.VISIBLE : View.GONE);
    }

    public abstract int layoutId();
    private ResultState resultState;
    public void show() {
        if(TextUtils.isEmpty(url())) {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
        }else {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url(), params(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String content) {
                    if (TextUtils.isEmpty(content)) {
//                    page_state_current=EMPTY_STATE_SET;
                        resultState = ResultState.EMPTY;
                        resultState.setContent("");
                    } else {
                        resultState = ResultState.SUCCESS;
                        resultState.setContent(content);
                    }
                    loadPage();

                }

                @Override
                public void onFailure(Throwable error, String content) {
                    resultState = ResultState.ERROR;
                    resultState.setContent("");
                    loadPage();
                }
            });
        }
    }

    private void loadPage() {
        switch (resultState) {
            case ERROR:
                page_state_current = PAGE_STATE_ERROR;
                break;
            case EMPTY:
                page_state_current = PAGE_STATE_EMPTY;
                break;
            case SUCCESS:
                page_state_current = PAGE_STATE_SUCCESS;
                break;
        }
        showSafePage();
        //如果是加载成功的状态
        if(page_state_current == PAGE_STATE_SUCCESS){
            onSuccess(resultState,successView);
        }
    }

    protected abstract void onSuccess(ResultState resultState, View successView);
    protected abstract RequestParams params();
    protected abstract String url();
    public enum ResultState {
        ERROR(2), EMPTY(3), SUCCESS(4);

        private int state;

        ResultState(int state) {
            this.state = state;
        }

        private String content;//保存的内部数据

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
