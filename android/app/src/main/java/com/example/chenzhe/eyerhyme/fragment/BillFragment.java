package com.example.chenzhe.eyerhyme.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.activity.PaymentActivity;
import com.example.chenzhe.eyerhyme.adapter.BillAdapter;
import com.example.greendao.dao.Bill;
import com.example.greendao.dao.BillDao;
import com.example.greendao.dao.DaoMaster;
import com.example.greendao.dao.DaoSession;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment {


    @Bind(R.id.listview)
    ListView listview;
    private SharedPreferences sharedPreferences;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;

    public BillFragment() {
        // Required empty public constructor
    }

    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private BillDao getBillDao() {
        return daoSession.getBillDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ButterKnife.bind(this, view);
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        int type = getArguments().getInt("type");
        if (type == 0) {
            setupDatabase();
            final List<Bill> bills = getBillDao().queryBuilder().where(BillDao.Properties.UserId.eq(sharedPreferences.getInt("user_id",-1)))
                    .orderDesc(BillDao.Properties.Id).build().list();
            Date curTime = new Date();
            for (int i = 0; i < bills.size();) {
                Bill bill = bills.get(i);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = formatter.parse(bill.getDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (curTime.getTime()-date.getTime() > 1000*900) {
                    getBillDao().delete(bill);
                    bills.remove(i);
                } else {
                    i++;
                }
            }
            BillAdapter adapter = new BillAdapter(bills, getContext());
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(getActivity(), PaymentActivity.class);
                    it.putExtra("subtotal", bills.get(position).getPrice());
                    ArrayList<Integer> ticket_id = new Gson().fromJson(bills.get(position).getTicketId(), new TypeToken<ArrayList<Integer>>(){}.getType());
                    it.putExtra("ticket_id", ticket_id);
                    startActivity(it);
                }
            });

        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
