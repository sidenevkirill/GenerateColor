package ru.lisdevs.generaterandom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

public class ImagePicker {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Activity activity;

    public ImagePicker(Activity activity) {
        this.activity = activity;
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void handleActivityResult(int requestCode, int resultCode, @Nullable Intent data, ImagePickListener listener) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Вы можете дополнительно преобразовать URI в Bitmap, если это необходимо
            listener.onImagePicked(imageUri);
        }
    }

    public interface ImagePickListener {
        void onImagePicked(Uri imageUri);
    }
}
