package org.martin.getfreaky.log;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.martin.getfreaky.MyPagerAdapter;
import org.martin.getfreaky.R;
import org.martin.getfreaky.dataObjects.DayLog;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by martin on 2016. 05. 19..
 */
public class ProgressPhotoFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Realm realm;
    private DayLog dayLog;
    private Bitmap image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress_photo, null);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String daylogId = preferences.getString(MyPagerAdapter.SELECTED_DAYLOG_ID, "NO_KEY");

        RealmConfiguration config = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
        dayLog = realm.where(DayLog.class).equalTo("dayLogId", daylogId).findFirst();

        Bitmap existingImage = dayLog.getProgressPicture().getImageBitmap();

        imageView = (ImageView) v.findViewById(R.id.progressPhoto);

        if (existingImage != null) {
            imageView.setImageBitmap(existingImage);
            this.image = existingImage;
        }

        FloatingActionButton fab =
                (FloatingActionButton) v.findViewById(R.id.photoButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        FloatingActionButton shareButton = (FloatingActionButton) v.findViewById(R.id.facebook_share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap imageToFb = image;
                if (imageToFb != null) {
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(imageToFb)
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    ShareDialog.show(getActivity(), content);
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            this.image = photo;
            imageView.setImageBitmap(photo);
            realm.beginTransaction();
            dayLog.getProgressPicture().setImage(photo);
            realm.commitTransaction();
        }
    }
}
