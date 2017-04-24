package com.adamzfc.androidbase.test.rxjava2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adamzfc.androidbase.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by adamzfc on 4/24/17.
 */

public class TestRxjava2Activity extends Activity {
    List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rxjava2);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, getData()));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                Method test = getClass().getDeclaredMethod(mDatas.get(position));
                test.invoke(TestRxjava2Activity.this);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> getData() {
        mDatas = new ArrayList<>();
        mDatas.add("test1");
        mDatas.add("test2");
        mDatas.add("test3");
        return mDatas;
    }

    // call by reflect
    @SuppressWarnings("unused")
    private void test1() {
        // create flowable
        List<String> list = Arrays.asList(
                "blue", "red", "green", "yellow", "orange", "cyan", "purple"
        );
        Flowable.fromIterable(list).skip(2).subscribe(System.out::println);
        Flowable.fromArray(list.toArray()).subscribe(System.out::println);
        Flowable.just("blue").subscribe(System.out::println);
    }

    // call by reflect
    @SuppressWarnings("unused")
    private void test2() {
        Flowable.create((FlowableOnSubscribe<Fib>) e -> {
            Fib start = new Fib(1L, 1L);

            while (!e.isCancelled()) {
                e.onNext(start);
                start = new Fib(start.b, start.fib());
            }
            e.onComplete();
        }, BackpressureStrategy.BUFFER).map(Fib::fib)
                .take(10).subscribe(System.out::println);

        Flowable.generate(() -> new Fib(1L, 1L), (x, y) -> {
            Fib fib = new Fib(x.b, x.fib());
            y.onNext(fib);
            return fib;
        }).ofType(Fib.class).map(Fib::fib).take(10).subscribe(System.out::println);
    }

    private class Fib {
        long a;
        long b;

        public Fib(long a, long b) {
            this.a = a;
            this.b = b;
        }

        public long fib() {
            return a + b;
        }
    }

    private void test3() {
        Observable<Integer> observable = Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onNext(4);

            e.onComplete();
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: ");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: All Done");
            }
        };

        observable.subscribe(observer);
    }
}
