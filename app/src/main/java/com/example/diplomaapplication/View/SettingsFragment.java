package com.example.diplomaapplication.View;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diplomaapplication.Model.UserModel;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.Repository.AuthRepository;
import com.example.diplomaapplication.ViewModel.AuthViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsFragment extends Fragment {

    private FirebaseUser user;
    NavController navController;
    UserModel userModel;
    AuthViewModel viewModel;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Users");

    private String userID;

    DocumentReference documentReference;
    private TextView tvUserName, tvUserUsername, percentCorrect, totalTests;
    private TextInputEditText tvUserNameDouble, tvUserUsernameDouble;
    private Button updateDataButton;
    private ImageButton goHomeButton, signOutButton;

    // for import and export photo to the storage
    private CircleImageView profileImage;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String name, username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view) {
        navController = Navigation.findNavController(view);
        // init for data from layout
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        tvUserName = view.findViewById(R.id.profileUserName);
        tvUserUsername = view.findViewById(R.id.profileUserUsername);
        tvUserNameDouble = view.findViewById(R.id.profileUserNameDouble);
        tvUserUsernameDouble = view.findViewById(R.id.profileUserUsernameDouble);
        percentCorrect = view.findViewById(R.id.percentCorrect);
        totalTests = view.findViewById(R.id.totalTests);

        // if user click on the imageView he can set new picture
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        profileImage = view.findViewById(R.id.profileUserPicture);
        profileImage.setOnClickListener(v -> {
            // Открываем галерею для выбора фото
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });

        // init data from DB
        documentReference = reference.document(userID);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            userModel = documentSnapshot.toObject(UserModel.class);
            if (userModel != null) {
                name = userModel.getName();
                username = userModel.getUsername();

                // Call updateDataOnLayout() here, after the data is loaded
                updateDataOnLayout();
            }
        });

        // for button "UPDATE"
        updateDataButton = view.findViewById(R.id.updateData);
        updateDataButton.setOnClickListener(view1 -> {
            if (isNameChanged() || isUsernameChanged()) {
                Toast.makeText(getActivity(), "Данные были обновлены", Toast.LENGTH_LONG).show();
                update();
            }
        });

        // for button "GO HOME"
        goHomeButton = view.findViewById(R.id.goHomeButton);
        goHomeButton.setOnClickListener(view13 -> navController.navigate(R.id.action_settingsFragment_to_homeFragment));

        // for button "SIGN OUT"
        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(view12 -> {
            viewModel.signOut();
            navController.navigate(R.id.action_settingsFragment_to_signInFragment);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Загрузка фото в Firebase Storage
            uploadProfilePhoto();
        }
    }

    private void uploadProfilePhoto() {
        if (imageUri != null) {
            // Создаем ссылку на место сохранения фото в Firebase Storage
            StorageReference profileImageRef = storageReference.child("profile_photos/" + userID + ".jpg");

            // Загружаем фото в Firebase Storage
            profileImageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Фото успешно загружено
                        Toast.makeText(getActivity(), "Фото успешно загружено", Toast.LENGTH_SHORT).show();

                        // Получаем ссылку на загруженное фото
                        profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String photoUrl = uri.toString();

                            // Обновляем ссылку на фото в поле profilePhoto документа пользователя
                            documentReference.update("profilePhoto", photoUrl)
                                    .addOnSuccessListener(unused -> {
                                        // Ссылка на фото успешно обновлена
                                        Toast.makeText(getActivity(), "Ссылка на фото успешно обновлена", Toast.LENGTH_SHORT).show();

                                        // Обновляем данные на экране
                                        updateDataOnLayout();

                                        // Загружаем новое фото в CircleImageView
                                        Glide.with(getActivity())
                                                .load(photoUrl)
                                                .into(profileImage);
                                    })
                                    .addOnFailureListener(e -> {
                                        // Обработка ошибки обновления ссылки на фото
                                        Toast.makeText(getActivity(), "Ошибка обновления ссылки на фото", Toast.LENGTH_SHORT).show();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Обработка ошибки загрузки фото
                        Toast.makeText(getActivity(), "Ошибка загрузки фото", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private boolean isUsernameChanged() {
        String newUsername = tvUserUsernameDouble.getText().toString();
        if (!newUsername.equals(username)) {
            documentReference.update("username", newUsername).addOnSuccessListener(unused -> {
                username = newUsername;
                Toast.makeText(getActivity(), "Имя пользователя обновлено", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                // Обработка ошибки обновления имени пользователя
                Toast.makeText(getActivity(), "Ошибка обновления имени пользователя", Toast.LENGTH_SHORT).show();
            });
            return true;
        }
        return false;
    }

    private boolean isNameChanged() {
        String newName = tvUserNameDouble.getText().toString();
        if (!newName.equals(name)) {
            documentReference.update("name", newName).addOnSuccessListener(unused -> {
                name = newName;
                Toast.makeText(getActivity(), "Имя обновлено", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                // Обработка ошибки обновления имени
                Toast.makeText(getActivity(), "Ошибка обновления имени", Toast.LENGTH_SHORT).show();
            });
            return true;
        }
        return false;
    }

    void update() {
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            userModel = documentSnapshot.toObject(UserModel.class);
            if (userModel != null) {
                name = userModel.getName();
                username = userModel.getUsername();
                updateDataOnLayout();
            }
        });
    }

    void updateDataOnLayout() {
        tvUserName.setText(name);
        tvUserNameDouble.setText(name);
        tvUserUsername.setText(username);
        tvUserUsernameDouble.setText(username);
        totalTests.setText(String.valueOf(userModel.getTestCount()));

        // Calculate percentage
        int correctSum = userModel.getCorrectSum();
        int wrongSum = userModel.getWrongSum();
        int percent = 0;
        if (correctSum + wrongSum > 0) {
            percent = 100 * correctSum / (correctSum + wrongSum);
        }
        percentCorrect.setText(String.valueOf(percent));

        // Загружаем фото пользователя, если ссылка на фото доступна
        if (userModel.getProfilePhoto() != null) {
            String photoUrl = userModel.getProfilePhoto();

            // Загружаем фото в CircleImageView
            Glide.with(getActivity())
                    .load(photoUrl)
                    .into(profileImage);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())).get(AuthViewModel.class);
    }
}