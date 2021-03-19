package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    Button b_save_img, b_choose_img, b_get_img, b_save_t, b_get_txt;
    EditText editText;
    TextView textView;
    ImageView set_img, get_img;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage storage;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set_img = (ImageView) findViewById(R.id.set_img);
        get_img = (ImageView) findViewById(R.id.get_img);
        b_get_img = (Button) findViewById(R.id.b_get_image);
        b_choose_img = (Button) findViewById(R.id.b_chhose_img);
        b_save_img = (Button) findViewById(R.id.b_save_img);
        b_save_t = (Button) findViewById(R.id.save_text);
        b_get_txt = (Button) findViewById(R.id.b_get_save_text);
        editText = (EditText) findViewById(R.id.add_string);
        textView = (TextView) findViewById(R.id.textview);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        b_get_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdataFirestore();
            }
        });
        b_save_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                myRef.setValue(msg);
            }
        });
        b_get_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String datax = snapshot.getValue().toString();
                        Log.d("gdff2  ", "dgdf " + datax);

                        if (datax != null) {
                            Log.d("gdff  ", "dgdf " + datax);
                            textView.setText(datax);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        b_choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        b_save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }

    private void getdataFirestore() {

        myRef = database.getReference("img");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String datax = snapshot.getValue().toString();
                    Log.d("gdff2  ", "dgdf " + datax);

                    if (datax != null) {
                        Log.d("gdff  ", "dgdf " + datax);
                        textView.setText(datax);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //   Picasso.get().load(upload.getImageUrl()).error(R.drawable.ic_launcher_background).into(binding.image);

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        mStorageRef = FirebaseStorage.getInstance().getReference("img");

        if (uri != null) {
            {

                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(uri));

                fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("grefd 1","GFd ");

                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                Log.d("grefd   ","GRfd  ");
                                //saveurltofirebase
                                myRef = database.getReference("img");

                                String msg = url;
                                myRef.setValue(msg);
                            }
                        });

                    }

                });



            }
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {

        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            try {
                intent.putExtra("return-data", true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE_REQUEST);
            } catch (ActivityNotFoundException e) {
            }
        } else {
            EasyPermissions.requestPermissions(this, "We need permissions because this and that",
                    123, perms);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
        }
        if (requestCode != RESULT_CANCELED && requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null) {

            System.out.println("aaaaaaa  " + data.getData());
            if (data.getData() != null) {
                Uri imageuri = data.getData();
                //cc
                uri = imageuri;
                set_img.setImageURI(uri);


            } else if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();

                int CurrentImageSelect = 0;

                while (CurrentImageSelect < count) {
                    Uri imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                    //cc
                    uri = imageuri;
                    set_img.setImageURI(uri);

                    CurrentImageSelect = CurrentImageSelect + 1;
                }
                //settt
                set_img.setImageURI(uri);
            }


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

}