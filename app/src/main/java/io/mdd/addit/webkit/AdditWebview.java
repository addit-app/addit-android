package io.mdd.addit.webkit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class AdditWebview extends WebView {
    public AdditWebview(Context context) {
        super(context);
    }

    public AdditWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdditWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AdditWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init() {

        Log.d("test","webview init 초기화 세팅@@");

        WebSettings settings = getSettings();
        // Javascript 사용하기
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // WebView 내장 줌 사용여부
        settings.setBuiltInZoomControls(true);
        // 화면에 맞게 WebView 사이즈를 정의
        settings.setLoadWithOverviewMode(true);
        // ViewPort meta tag를 활성화 여부
        settings.setUseWideViewPort(true);
        // 줌 컨트롤 사용 여부
        settings.setDisplayZoomControls(false);
        // 사용자 제스처를 통한 줌 기능 활성화 여부
        settings.setSupportZoom(false);
        // TextEncoding 이름 정의
        settings.setDefaultTextEncodingName("UTF-8");

        // Setting Local Storage
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        // 캐쉬 사용 방법을 정의
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");




    }
}
