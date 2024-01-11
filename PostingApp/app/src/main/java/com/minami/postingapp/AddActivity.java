package com.minami.postingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.minami.postingapp.api.NetworkClient;
import com.minami.postingapp.api.PostingApi;
import com.minami.postingapp.config.Config;
import com.minami.postingapp.model.Res;
import com.minami.postingapp.R;
import com.minami.postingapp.api.PostingApi;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddActivity extends AppCompatActivity {

    ImageView imgPhoto;
    EditText editContent;
    Button btnSave;

    // 사진 파일!!
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imgPhoto = findViewById(R.id.imgPhoto);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editContent.getText().toString().trim();

                if (photoFile == null || content.isEmpty()) {
                    Toast.makeText(AddActivity.this,
                            "사진과 내용을 모두 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 네트워크로 API 호출
                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(AddActivity.this);

                PostingApi api = retrofit.create(PostingApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString("token", "");
                token = "Bearer " + token;

                // 보낼 파일
                RequestBody fileBody = RequestBody.create(photoFile, MediaType.parse("image/jpg"));
                MultipartBody.Part image = MultipartBody.Part.createFormData("image", photoFile.getName(), fileBody);

                // 보낼 텍스트
                RequestBody textBody = RequestBody.create(content, MediaType.parse("text/plain"));

                Call<Res> call = api.addPosting(token, image, textBody);

                call.enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Call<Res> call, Response<Res> response) {
                        dismissProgress();

                        if (response.isSuccessful()) {

                            finish();

                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<Res> call, Throwable t) {
                        dismissProgress();
                    }
                });

            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle(R.string.alert_title);
        builder.setItems(R.array.alert_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i == 0) {
                    // 첫번째 항목 : 카메라 실행
                    camera();
                } else if (i == 1) {
                    // 두번째 항목 : 앨범에서 선택
                    album();
                }

            }
        });
        builder.show();
    }

    private void camera() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                AddActivity.this, Manifest.permission.CAMERA);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddActivity.this,
                    new String[]{android.Manifest.permission.CAMERA},
                    1000);
            Toast.makeText(AddActivity.this, "카메라 권한 필요합니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(AddActivity.this.getPackageManager()) != null) {

                // 사진의 파일명을 만들기
                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                photoFile = getPhotoFile(fileName);

                // todo : 패키지명을, 현재의 프로젝트에 맞게 수정해야 함.
                Uri fileProvider = FileProvider.getUriForFile(AddActivity.this,
                        "com.block.postingapp.fileprovider", photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
                startActivityForResult(i, 100);

            } else {
                Toast.makeText(AddActivity.this, "이폰에는 카메라 앱이 없습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 500: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    private File getPhotoFile(String fileName) {
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(fileName, ".jpg", storageDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(photoFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            photo = rotateBitmap(photo, orientation);

            // 압축시킨다. 해상도 낮춰서
            OutputStream os;
            try {
                os = new FileOutputStream(photoFile);
                photo.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            imgPhoto.setImageBitmap(photo);
            imgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // 네트워크로 데이터 보낸다.


        } else if (requestCode == 300 && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {

            Uri albumUri = data.getData();
            String fileName = getFileName(albumUri);
            try {

                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(albumUri, "r");
                if (parcelFileDescriptor == null) return;
                FileInputStream inputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                photoFile = new File(this.getCacheDir(), fileName);
                FileOutputStream outputStream = new FileOutputStream(photoFile);
                IOUtils.copy(inputStream, outputStream);

//                //임시파일 생성
//                File file = createImgCacheFile( );
//                String cacheFilePath = file.getAbsolutePath( );


                // 압축시킨다. 해상도 낮춰서
                Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                OutputStream os;
                try {
                    os = new FileOutputStream(photoFile);
                    photo.compress(Bitmap.CompressFormat.JPEG, 60, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                imgPhoto.setImageBitmap(photo);
                imgPhoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

//                imageView.setImageBitmap( getBitmapAlbum( imageView, albumUri ) );

            } catch (Exception e) {
                e.printStackTrace();
            }

            // 네트워크로 보낸다.
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    private void album() {
        if (checkPermission()) {
            displayFileChoose();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.i("DEBUGGING5", "true");
            Toast.makeText(AddActivity.this, "권한 수락이 필요합니다.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.i("DEBUGGING6", "false");
            ActivityCompat.requestPermissions(AddActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 500);
        }
    }

    private void displayFileChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), 300);
    }

    //앨범에서 선택한 사진이름 가져오기
    public String getFileName(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        try {
            if (cursor == null) return null;
            cursor.moveToFirst();
            @SuppressLint("Range") String fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            cursor.close();
            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            return null;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AddActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_DENIED) {
            return false;
        } else {
            return true;
        }
    }

    Dialog dialog;

    void showProgress() {
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(new ProgressBar(this));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    void dismissProgress() {
        dialog.dismiss();
    }

}