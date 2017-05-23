package bree.com.bewwweibo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import bree.com.bewwweibo.R;

public class PhotoViewActivity extends BaseActivity {

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getToorBarX().setTitle(R.string.lbl_home);
        String photo_url = getIntent().getStringExtra("Photo_url");
        Glide.with(this).load(photo_url).into(photoView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_view;
    }
   private void initView(){
       photoView = (PhotoView) findViewById(R.id.photoview);

   }
}
