package com.lgcampos.carros.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lgcampos.carros.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteLivroFragment extends Fragment {

    private static final String URL_SOBRE = "http://www.livroandroid.com.br/sobre.htm";
    private WebView webView;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_site_livro, container, false);

        webView = (WebView) view.findViewById(R.id.webview);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        setWebViewClient(webView);
        webView.loadUrl(URL_SOBRE);

        swipeRefresh.setOnRefreshListener(onRefreshListener());

        swipeRefresh.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);


        configJavaScript();
        return view;
    }

    private void configJavaScript() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LivroAndroidInterface(), "LivroAndroid");
    }

    @NonNull
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        };
    }

    public void setWebViewClient(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.INVISIBLE);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("livroandroid", "webview url: " + url);
                if (url != null && url.endsWith("sobre.htm")) {
                    AboutDialog.showAbout(getFragmentManager());
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }


    class LivroAndroidInterface {
        @JavascriptInterface
        public void sobre() {
            Log.d("TAG", "Clicou na figura do livro!");
        }
    }
}
