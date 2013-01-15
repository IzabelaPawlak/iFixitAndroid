package com.dozuki.ifixit.guide_create.ui;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.EditText;

import com.dozuki.ifixit.MainApplication;
import com.dozuki.ifixit.R;
import com.dozuki.ifixit.gallery.ui.GalleryActivity;
import com.dozuki.ifixit.guide_create.model.GuideCreateStepObject;
import com.ifixit.android.imagemanager.ImageManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

public class GuideCreateEditMediaFragment extends Fragment implements
OnClickListener, OnLongClickListener {
   
   private static  String NO_TITLE = "Title";
   public static final int FETCH_IMAGE_KEY = 1;
   public static final String THUMB_POSITION_KEY = "THUMB_POSITION_KEY";

   
   EditText mStepTitle;
   ImageManager mImageManager;
   ImageView mLargeImage;
   ImageView mImageOne;
   ImageView mImageTwo;
   ImageView mImageThree;
   String mTitle = NO_TITLE;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mImageManager = ((MainApplication) getActivity().getApplication())
            .getImageManager();
   }
   
   
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, 
       Bundle savedInstanceState) {
       // Inflate the layout for this fragment
      View v = inflater.inflate(R.layout.guide_create_edit_media, container, false);
      mStepTitle = (EditText) v.findViewById(R.id.step_edit_title_text);
      mLargeImage = (ImageView) v.findViewById(R.id.step_edit_large_image);
      mImageOne = (ImageView) v.findViewById(R.id.step_edit_thumb_1);
      mImageOne.setOnLongClickListener(this);
      mImageOne.setOnClickListener(this);
      mImageTwo = (ImageView) v.findViewById(R.id.step_edit_thumb_2);
      mImageTwo.setOnLongClickListener(this);
      mImageTwo.setOnClickListener(this);
      mImageThree = (ImageView) v.findViewById(R.id.step_edit_thumb_3);
      mImageThree.setOnLongClickListener(this);
      mImageThree.setOnClickListener(this);
      
      
      mStepTitle.setText(mTitle);
      mStepTitle.addTextChangedListener(new TextWatcher() {

         @Override
         public void afterTextChanged(Editable s) {

         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count,
               int after) {
            // TODO Auto-generated method stub
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before,
               int count) {
            Log.i("GuideCreateStepEditFragment", "GuideTitle changed to: "
                  + s.toString());
            mTitle = s.toString();
         }

      });
      
      
       return v;
   }

   
   public void setStepTitle(String title) {
      mTitle = title;
      if(mStepTitle != null)
         mStepTitle.setText(mTitle);
   }

   @Override
   public void onSaveInstanceState(Bundle savedInstanceState) {
      super.onSaveInstanceState(savedInstanceState);
     // savedInstanceState.putSerializable(
     //       GuideCreateStepEditFragment.GuideEditKey, mStepObject);
    //  savedInstanceState.putBoolean(ReorderStepsKey, mReorderStepsMode);
   }

   @Override
   public void onClick(View v) {
      String microURL = null;
      switch (v.getId()) {
      case R.id.step_edit_thumb_1:
         microURL = (String) mImageOne.getTag();
         break;
      case R.id.step_edit_thumb_2:
         microURL = (String) mImageTwo.getTag();
         break;
      case R.id.step_edit_thumb_3:
         microURL = (String) mImageThree.getTag();
         break;
      case R.id.step_edit_thumb_media:
         break;
      default:
         return;
      }
      if (microURL != null) {
         mImageManager.displayImage(microURL, getActivity(), mLargeImage);
         mLargeImage.invalidate();
      }
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      switch (requestCode) {
      case FETCH_IMAGE_KEY:
         if (resultCode == Activity.RESULT_OK) {
            String mediaURL = data
                  .getStringExtra(GuideCreateStepsEditActivity.MediaReturnKey);
            int mediaSlot = data.getIntExtra(
                  GuideCreateStepsEditActivity.MediaSlotReturnKey, -1);
            Log.i("editstep", mediaSlot + "");
            setImage(mediaSlot, mediaURL);
         }
         break;
      }
   }

   private void setImage(int location, String url) {
      switch (location) {
      case 1:
         mImageManager.displayImage(url, getActivity(), mImageOne);
         mImageOne.setTag(url);
         mImageOne.invalidate();
         break;
      case 2:
         mImageManager.displayImage(url, getActivity(), mImageTwo);
         mImageTwo.setTag(url);
         mImageTwo.invalidate();
         break;
      case 3:
         mImageManager.displayImage(url, getActivity(), mImageThree);
         mImageThree.setTag(url);
         mImageThree.invalidate();
         break;
      default:
         return;
      }
      mImageManager.displayImage(url, getActivity(), mLargeImage);
      mLargeImage.invalidate();
     // mStepObject.getImages().get(location - 1).setText(url);
   }

   @Override
   public boolean onLongClick(View v) {
      Intent intent;
      switch (v.getId()) {
      case R.id.step_edit_thumb_1:
         intent = new Intent(getActivity(), GalleryActivity.class);
         intent.putExtra(THUMB_POSITION_KEY, 1);
         startActivityForResult(intent, FETCH_IMAGE_KEY);
         break;
      case R.id.step_edit_thumb_2:
         intent = new Intent(getActivity(), GalleryActivity.class);
         intent.putExtra(THUMB_POSITION_KEY, 2);
         startActivityForResult(intent, FETCH_IMAGE_KEY);
         break;
      case R.id.step_edit_thumb_3:
         intent = new Intent(getActivity(), GalleryActivity.class);
         intent.putExtra(THUMB_POSITION_KEY, 3);
         startActivityForResult(intent, FETCH_IMAGE_KEY);
         break;
      case R.id.step_edit_thumb_media:
         break;
      }
      return true;
   }

}