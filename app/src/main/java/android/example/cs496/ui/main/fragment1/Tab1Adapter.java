package android.example.cs496.ui.main.fragment1;

import android.content.Context;
import android.example.cs496.R;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Tab1Adapter extends RecyclerView.Adapter<Tab1Adapter.MyViewHolder> {

    private List<RecyclerItem> datas;
    private final Context context;

    public Tab1Adapter(Context context, List<RecyclerItem> datas){
        this.datas = datas;
        this.context = context;
    }

    public void removeItem(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datas.size());
    }
    public void restoreItem(RecyclerItem model, int position) {
        datas.add(position, model);
        // notify item added by position
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View holderView = inflater.inflate(R.layout.fragment1_holder_view, viewGroup, false);
        return new MyViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i){

        RecyclerItem data = datas.get(i);
        System.out.println(i);
        System.out.println(data.getName());
        myViewHolder.textView.setText(data.getName());
        myViewHolder.phoneView.setText(data.getPhone());
        ShapeDrawable bgShape;
        int colorPrimaryLight = context.getResources().getColor(R.color.colorPrimaryLight);
        if ( i == 0 ){
            myViewHolder.imageView.setPadding(20, 20, 20, 20);
            //int colorPrimaryLight = context.getResources().getColor(R.color.colorPrimaryLight);
            myViewHolder.cardView.setCardBackgroundColor(colorPrimaryLight);
            myViewHolder.textView.setText("Add a New Friend:)");
            myViewHolder.textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            myViewHolder.textView.setTextColor(Color.WHITE);
            myViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_addperson));
            //myViewHolder.imageView.setImageResource(data.getImg());
            bgShape = new ShapeDrawable(new OvalShape());
            bgShape.setTint(Color.WHITE);
        }
        else {
            myViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_foreground));
            //myViewHolder.imageView.setImageResource(data.getImg());
            bgShape = new ShapeDrawable(new OvalShape());
            bgShape.setTint(colorPrimaryLight);
        }
        myViewHolder.imageView.setBackground(bgShape);
        myViewHolder.imageView.setClipToOutline(true);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        public TextView phoneView;
        public CardView cardView;
        OnClickListener onClickListener;

        public MyViewHolder(@NonNull View view){
            super(view);
            this.imageView = view.findViewById(R.id.iv_pic);
            this.textView = view.findViewById(R.id.tv_text);
            this.phoneView = view.findViewById(R.id.tv_phone);
            this.cardView = view.findViewById(R.id.sub_card);
        }

        @Override
        public void onClick(View view){
            onClickListener.onPhoneClick(getAdapterPosition());
        }
    }

    public interface OnClickListener {
        void onPhoneClick(int position);
    }

//    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
//        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
//        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
//        if(input != null)
//            return resizingBitmap(BitmapFactory.decodeStream(input));
//        else
//            Log.d("PHOTO", "first try failed to load photo");
//
//        byte[] photoBytes = null;
//        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
//        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
//        try {
//            if(c.moveToFirst())
//                photoBytes = c.getBlob(0);
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            c.close();
//        }
//
//        if(photoBytes != null)
//            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
//        else
//            Log.d("PHOTO", "second try also failed");
//        return null;
//    }
//
//    public Bitmap resizingBitmap(Bitmap oBitmap){
//        if(oBitmap == null)return null;
//        float width = oBitmap.getWidth();
//        float height = oBitmap.getHeight();
//        float resizing_size = 250;
//        Bitmap rBitmap = null;
//        if(width > resizing_size){
//            float mWidth = (float) (width / 100);
//            float fScale = (float) (resizing_size / mWidth);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//        }
//        if(height > resizing_size){
//            float mHeight = (float) (height / 100);
//            float fScale = (float) (resizing_size / mHeight);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//        }
//
//        Log.d("PHOTO", "rBitmap : " + width + ", " + height);
//        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int)height, true);
//        return rBitmap;
//    }
}
