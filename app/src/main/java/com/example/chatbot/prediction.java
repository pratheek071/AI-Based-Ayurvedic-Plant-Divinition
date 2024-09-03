package com.example.chatbot;

import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.example.chatbot.ml.Model;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class prediction extends AppCompatActivity {
    TextView result,link;
    ImageView imageView;
    Button picture, gallery;

    private TextView name;
    private TextView sanskrit;
    private TextView benefits;
    private TextView habitat;
    private TextView parts;
    private DatabaseReference databaseReference;
    int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prediction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        result = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView2);
        picture = findViewById(R.id.button3);
        gallery = findViewById(R.id.button4);
        name = findViewById(R.id.name);
        sanskrit = findViewById(R.id.Sanskrit);
        habitat = findViewById(R.id.habitat);
        benefits = findViewById(R.id.benefits);
        parts = findViewById(R.id.parts);
        link=findViewById(R.id.link);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        databaseReference = FirebaseDatabase.getInstance().getReference();

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 3);
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }

    private void retrieveData() {
        String Name = result.getText().toString().trim();

        if (!Name.isEmpty()) {
            DatabaseReference userRef = databaseReference.child("Ayurvedic Plants").child(Name);

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        String Sanskrit = dataSnapshot.child("Sanskrit Name").getValue(String.class);
                        String Parts = dataSnapshot.child("Parts Used").getValue(String.class);
                        String Habitat = dataSnapshot.child("Habitat").getValue(String.class);
                        String Benefits = dataSnapshot.child("Medicinal Benefits").getValue(String.class);

                        name.setText(Name);
                        habitat.setText(Habitat);
                        benefits.setText(Benefits);
                        sanskrit.setText(Sanskrit);
                        parts.setText(Parts);
                    } else {
                        name.setText("Not Found");
                        sanskrit.setText("Not Found");
                        habitat.setText("Not Found");
                        benefits.setText("Not Found");
                        parts.setText("Not Found");
                    }
                } else {
                    name.setText("Error retrieving data");
                    sanskrit.setText("");
                    habitat.setText("");
                    benefits.setText("");
                    parts.setText("");
                }
            });
        } else {
            name.setText("Please enter a name");
            sanskrit.setText("");
            benefits.setText("");
            habitat.setText("");
            parts.setText("");
        }
    }

    public void classifyImage(Bitmap image) {
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            if (maxConfidence < 0.8) {
                Toast.makeText(getApplicationContext(), " Please find a link below for more information!", Toast.LENGTH_SHORT).show();
                // Clear displayed data
                result.setText("");name.setText("");sanskrit.setText("");benefits.setText("");habitat.setText("");parts.setText("");

            } else {
                String[] classes = {"Aloevera", "Adulsa", "Amruth Balli", "Arjun Chal", "Arka", "Aruta", "Brahmi", "Cardamom", "Chikku", "Chittarada", "Dantapala", "Insulin Plant", "Jaikai", "Kallu Baale", "Kasturi Turmeric ", "Keshakanthi", "Kodubelu", "Madhunashini", "Munya", "Neelambari", "Neem", "Niri Gundi", "Pepper", "Nouni", "Ondelaga", "Raktha Sandhu", "Sarvasugandh", "Shatavadi", "Spatika", "Tulsi", "Vatamkulli"};
                result.setText(classes[maxPos]);
            }
            model.close();
        } catch (IOException e) {
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        } else {
            Uri dat = data.getData();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }

        super.onActivityResult(requestCode, resultCode, data);

        // Clear displayed data if image is not predicted successfully
        if (result.getText().toString().equals("Please enter a name") || result.getText().toString().equals("Error retrieving data") || result.getText().toString().isEmpty()) {
            name.setText("");
            sanskrit.setText("");
            benefits.setText("");
            habitat.setText("");
            parts.setText("");
        }
    }
}

