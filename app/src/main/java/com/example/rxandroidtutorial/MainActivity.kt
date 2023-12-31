package com.example.rxandroidtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.core.Observer as Observer


class MainActivity : AppCompatActivity() {

    private lateinit var mDisposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable = getObservable()
        val observer = getObserverUsers()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun getObserverUsers(): Observer<Long> {
        return object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                Log.e("tien dung", "onSubscribe")
                mDisposable=d
            }

            override fun onNext(t: Long) {
                Log.e("tien dung", "onNext: $t")
//                if(t is Array<*>){
//                    if(t.isArrayOf<User>()){
//                        val users = t as Array<User>
//                        for (user in users){
//                            Log.e("tien dung", "User info onNext: ${user.toString()}")
//                        }
//                    }
//                }
                if(t.toInt()==3){
                    mDisposable.dispose()
                }
            }

            override fun onError(e: Throwable) {
                Log.e("tien dung", "onError")
            }

            override fun onComplete() {
                Log.e("tien dung", "onComplete")
            }
        }
    }

    private fun getObservable(): Observable<Long> {
//        val listUser = getListUsers()
//        return Observable.create { emitter ->
//            if (listUser == null) {
//                if (!emitter.isDisposed) {
//                    emitter.onError(Exception())
//                }
//            }
//            for (user in listUser) {
//                if (!emitter.isDisposed) {
//                    emitter.onNext(user)
//                }
//            }
//            if (!emitter.isDisposed) {
//                emitter.onComplete()
//            }
//        }
        val user1 =User(1, "dung 1")
        val user2 =User(2, "dung 2")
        val arrayUser = arrayOf(user1, user2)
//        return Observable.fromArray(user1, user2)
        return Observable.interval(3,5, TimeUnit.SECONDS)

    }

    private fun getListUsers(): List<User> {
        val list = mutableListOf<User>()
        list.add(User(1, "dung1"))
        list.add(User(2, "dung2"))
        list.add(User(3, "dung3"))
        list.add(User(4, "dung4"))
        list.add(User(5, "dung5"))

        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mDisposable !=null){
            mDisposable.dispose()
        }
    }
}