package android.example.cs496.ui.main;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.MainBackPressCloseHandler;
import android.example.cs496.ui.main.fragment1.PhoneBookActivity;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.example.cs496.ui.main.fragment1.RecyclerItemClickListener;
import android.example.cs496.ui.main.fragment1.Tab1Adapter;
import android.example.cs496.ui.main.fragment1.phonebook.EditPhoneBook;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static android.example.cs496.ui.main.fragment1.dummyData.refreshData;


public class TabFragment1 extends Fragment {

    private static Context context;
    RecyclerView recyclerView;
    static List<RecyclerItem> datas = null;
    private MainBackPressCloseHandler mainBackPressCloseHandler;
    private Paint p = new Paint();
    Tab1Adapter adapter;
    //RecyclerView.Adapter adapter;
    //RecyclerView.LayoutManager layoutManager;
   // private ArrayList<RecyclerItem> tap1Items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        mainBackPressCloseHandler = new MainBackPressCloseHandler(getActivity());

        View v = inflater.inflate(R.layout.tab_fragment1,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        resetData();

        enableSwipe();

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //layoutManager.scrollToPositionWithOffset(0,0);
                //recyclerView.smoothScrollToPosition(0);
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView,new RecyclerView.State(), 0);


            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy==0){
                    fab.hide();

                }else {
                    fab.show();
                }
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context.getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(position == 0){
                            Intent intent = new Intent(context, EditPhoneBook.class);
                            RecyclerItem item = new RecyclerItem();
                            intent.putExtra("select", item);
                            intent.putExtra("state", 1);
                            startActivityForResult(intent, 0);
                        }
                        else {
                            Intent intent = new Intent(context, PhoneBookActivity.class);
                            RecyclerItem item = datas.get(position);
                            intent.putExtra("select", item);
                            startActivityForResult(intent, 0);
                        }
                    }
                }));
        return v;
    }
    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT && viewHolder.getAdapterPosition()!=0){
                    int position = viewHolder.getAdapterPosition();
                    final RecyclerItem deletedModel = datas.get(position);
                    final int deletedPosition = position;
                    // showing snack bar with Undo option
                    adapter.removeItem(position);
                    Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), " removed from Contacts!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // undo is selected, restore the deleted item
                            adapter.restoreItem(deletedModel, deletedPosition);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_trash);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resetData();
    }

    public void resetData(){
        datas = refreshData();
        adapter = new Tab1Adapter(context, datas);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        //layoutManager.scrollToPositionWithOffset(0,0);
        recyclerView.setLayoutManager(layoutManager);

    }
}