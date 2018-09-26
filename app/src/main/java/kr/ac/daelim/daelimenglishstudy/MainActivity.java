package kr.ac.daelim.daelimenglishstudy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    TextView errorText;
    Button btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorText = (TextView) findViewById(R.id.net_error_view);
        webView = (WebView) findViewById(R.id.webview_main);
        btn_connect = (Button) findViewById(R.id.btn_connect);


        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);                 //js 허용
        webSettings.setLoadWithOverviewMode(true);              //html 컨텐츠가 웹뷰보다 스면 스크린 크기에 맞게 조정
        webSettings.setUseWideViewPort(true);                   //웹뷰가 html의 viewport 메타태그를 지원
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);    //웹뷰가 캐시를 사용하지 않도록 설정
        webSettings.setDomStorageEnabled(true);                 //html5의 localstorage 기능 사용
        webView.setScrollbarFadingEnabled(true);                //스크롤바 없앰


        //하드웨어 랜더링
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //네트워크 연결에러 시
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                switch (errorCode) {

                    case ERROR_AUTHENTICATION:
                        break;               // 서버에서 사용자 인증 실패

                    case ERROR_BAD_URL:
                        break;               // 잘못된 URL

                    case ERROR_CONNECT:
                        break;               // 서버로 연결 실패

                    case ERROR_FAILED_SSL_HANDSHAKE:
                        break;               // SSL handshake 수행 실패

                    case ERROR_FILE:
                        break;               // 일반 파일 오류

                    case ERROR_FILE_NOT_FOUND:
                        break;               // 파일을 찾을 수 없습니다

                    case ERROR_HOST_LOOKUP:
                        break;               // 서버 또는 프록시 호스트 이름 조회 실패

                    case ERROR_IO:
                        break;               // 서버에서 읽거나 서버로 쓰기 실패

                    case ERROR_PROXY_AUTHENTICATION:
                        break;               // 프록시에서 사용자 인증 실패

                    case ERROR_REDIRECT_LOOP:
                        break;               // 너무 많은 리디렉션

                    case ERROR_TIMEOUT:
                        break;               // 연결 시간 초과

                    case ERROR_TOO_MANY_REQUESTS:
                        break;               // 페이지 로드중 너무 많은 요청 발생

                    case ERROR_UNKNOWN:
                        break;               // 일반 오류

                    case ERROR_UNSUPPORTED_AUTH_SCHEME:
                        break;               // 지원되지 않는 인증 체계

                    case ERROR_UNSUPPORTED_SCHEME:
                        break;               // url이 지원되지 않는 방식
                }

                super.onReceivedError(view, errorCode, description, failingUrl);

                webView.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
                btn_connect.setVisibility(View.VISIBLE);
            }
        });

        webView.loadUrl("http://61.84.24.188:8080/word/");

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                startActivity(intent);
            }
        });

    }


    BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

    @Override
    public void onBackPressed() {
        if (webView.getOriginalUrl().equalsIgnoreCase(URL)) {
            backPressCloseHandler.onBackPressed();
        }else if(webView.canGoBack()){
            webView.goBack();
        }else{
            backPressCloseHandler.onBackPressed();
        }
    }
}
