package android.example.cs496.ui.main.fragment2;

import android.database.Cursor;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import java.io.Serializable;

public class PhotoItem implements Serializable {

    private Uri photoUri;
    private int photoId;
    private String Id;

    public PhotoItem(){}
    public PhotoItem(Uri photoUri, int photoId, String Id){
        this.photoUri = photoUri;
        this.photoId = photoId;
        this.Id = Id;
    }
    public void setUri(Uri photoUri){ this.photoUri = photoUri; }
    public void setPhotoId(int photoId){ this.photoId = photoId; }
    public void setId(String Id){this.Id = Id; }
    public Uri getUri(){ return this.photoUri; }
    public int getPhotoId(){ return this.photoId; }
    public String getId(){ return this.Id; }


}
