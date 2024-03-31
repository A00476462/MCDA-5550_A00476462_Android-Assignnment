package com.example.hotelreservation;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;

public class HotelSearchFragment extends Fragment {
    View view;
    TextView titleTextView;
    Button searchButton, clearButton;
    DatePicker checkInDatePicker, checkOutDatePicker;
    String checkInDate, checkOutDate, numberOfGuests;
    EditText guestsCountEditText, nameEditText;
    ConstraintLayout mainLayout;

    //这是一个方法，View对象的方法
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //这是第一次加载视图时调用的方法，填充Fragment界面，展示的界面叫：hotel_search_layout，而且必须要有返回值
        view = inflater.inflate(R.layout.hotel_search_layout,container,false);
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mainLayout =view.findViewById(R.id.main_layout);
        guestsCountEditText =view.findViewById(R.id.number_input);

        titleTextView = view.findViewById(R.id.title_text_view);
        titleTextView.setText(R.string.welcome_to_lilian_hotel);

        nameEditText = view.findViewById(R.id.guest_name);

        checkInDatePicker = view.findViewById(R.id.checkin_calendar_view);
        checkOutDatePicker = view.findViewById(R.id.checkout_calendar_view);

        searchButton = view.findViewById(R.id.search_button);
        clearButton = view.findViewById(R.id.clear_button);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkInDate = getDateFromCalendar(checkInDatePicker);
                checkOutDate = getDateFromCalendar(checkOutDatePicker);
                numberOfGuests = guestsCountEditText.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("check in date", checkInDate);//将checkInDate的值存储在check in date中，以便后续使用
                bundle.putString("check out date", checkOutDate);
                bundle.putString("number of guests", numberOfGuests);

                HotelsListFragment hotelsListFragment = new HotelsListFragment();
                hotelsListFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_layout, hotelsListFragment);
                fragmentTransaction.remove(HotelSearchFragment.this);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestsCountEditText.setText("");
                nameEditText.setText("");
            }
        });

    }
    private String getDateFromCalendar(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");//如果这里MM都是小写，则会有问题
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        return formattedDate;
    }
}
