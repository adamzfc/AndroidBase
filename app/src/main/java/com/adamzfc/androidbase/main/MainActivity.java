package com.adamzfc.androidbase.main;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.adamzfc.androidbase.R;
import com.adamzfc.router.annotation.Router;

import javax.inject.Inject;

import common.CommonAcitivity;
import common.constants.IntentC;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
@Router(IntentC.MAIN)
public class MainActivity extends CommonAcitivity {

    @Inject MainPresenter mPresenter;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ActivityRouter.bind(this);
//        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                ActivityRouter.go(MainActivity.this, IntentC.SECOND, null, v);
//            }
//        });
//    }

    @Override
    protected void initView() {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, mainFragment);
            transaction.commit();
        }
        DaggerMainComponet.builder()
                .mainPresenterModule(new MainPresenterModule(mainFragment)).build()
                .inject(this);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable.just("one", "two", "three")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        System.out.print(value);
                        Log.d("test", value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
