package com.adamzfc.androidbase.test.rxjava2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adamzfc.androidbase.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adamzfc on 4/24/17.
 */

public class TestRxjava2Activity extends Activity {
    List<String> mDatas;
    private Logger logger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = LoggerFactory.getLogger(TestRxjava2Activity.class);
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
        mDatas.add("test4");
        mDatas.add("test5");
        mDatas.add("test6");
        mDatas.add("test7");
        mDatas.add("test8");
        mDatas.add("test9");
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

    // call by reflect
    @SuppressWarnings("unused")
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
                logger.info("onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                logger.info("onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                logger.info("onError: ");
            }

            @Override
            public void onComplete() {
                logger.info("onComplete: All Done");
            }
        };

        observable.subscribe(observer);
    }

    // call by reflect
    @SuppressWarnings("unused")
    private void test4() {
        Observable<Integer> observable = Observable.create(e -> {
            logger.info("Observable thread is : {}", Thread.currentThread().getName());
            logger.info("emit 1");
            e.onNext(1);
        });
        Consumer<Integer> consumer = (integer -> {
            logger.info("Observable thread is : {}", Thread.currentThread().getName());
            logger.info("onNext: {}", integer);
        });
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    //map
    @SuppressWarnings("unused")
    private void test5() {
        Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).map(integer -> "This is result " + integer)
                .subscribe(s -> logger.info(s));
    }

    //FlatMap
    @SuppressWarnings("unused")
    private void test6() {
        Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).flatMap(integer -> {
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("I am value " + integer);
            }
            return Observable.fromIterable(list).delay(10, TimeUnit.MICROSECONDS);
        }).subscribe(s -> logger.info(s));
    }

    //concatMap
    @SuppressWarnings("unused")
    private void test7() {
        Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).concatMap(integer -> {
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("I am value " + integer);
            }
            return Observable.fromIterable(list).delay(10, TimeUnit.MICROSECONDS);
        }).subscribe(s -> logger.info(s));
    }

    // zip
    @SuppressWarnings("unused")
    private void test8() {
        Observable<Integer> observable1 = Observable.create((ObservableOnSubscribe<Integer>) e -> {
            logger.info("emit 1");
            e.onNext(1);

            logger.info("emit 2");
            e.onNext(2);

            logger.info("emit 3");
            e.onNext(3);

            logger.info("emit 4");
            e.onNext(4);

            logger.info("emit complete1");
            e.onComplete();
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create((ObservableOnSubscribe<String>) e -> {
            logger.info("emit A");
            e.onNext("A");

            logger.info("emit B");
            e.onNext("B");

            logger.info("emit C");
            e.onNext("C");

            logger.info("emit complete2");
            e.onComplete();
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, (integer, s) -> integer + s)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logger.info("onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        logger.info("onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info("onError");
                    }

                    @Override
                    public void onComplete() {
                        logger.info("onComplete");
                    }
                });
    }

    // flowable
    @SuppressWarnings("unused")
    private void test9() {
        Flowable<Integer> upstream = Flowable.create(e -> {
            logger.info("current requested: " + e.requested());
            logger.info("emit 1");
            e.onNext(1);
            logger.info("emit 2");
            e.onNext(2);
            logger.info("emit 3");
            e.onNext(3);
            logger.info("emit complete");
            e.onComplete();
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> downstrem = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                logger.info("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                logger.info("onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("onError: {}", t);
            }

            @Override
            public void onComplete() {
                logger.info("onComplete");
            }
        };
        upstream.subscribe(downstrem);
    }

}
