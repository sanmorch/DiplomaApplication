package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.diplomaapplication.Adapter.CourseListAdapter;
import com.example.diplomaapplication.Course;
import com.example.diplomaapplication.Model.UserModel;
import com.example.diplomaapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Users");
    private DocumentReference documentReference;
    private UserModel userModel;
    private String userPhoto;
    
    private RecyclerView recyclerView;
    private CircleImageView settingsButton;
    private CourseListAdapter listAdapter;
    private ArrayList<Course> courseArrayList = new ArrayList<>();

    // static data for my courses
    private int[] imageList =
            {R.drawable.first_course_new_menu, R.drawable.first_course_new_menu,
                    R.drawable.second_course_new_menu, R.drawable.second_course_new_menu,
                    R.drawable.third_course_new_menu, R.drawable.third_course_new_menu,
                    R.drawable.fourth_course_new_menu, R.drawable.fourth_course_new_menu};
    private String[] nameList = {"1 курс 1 семестр", "1 курс 2 семестр", "2 курс 1 семестр",
            "2 курс 2 семестр", "3 курс 1 семестр", "3 курс 2 семестр", "4 курс 1 семестр",
            "4 курс 2 семестр"};
    private Course course;

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        for (int i = 0; i < imageList.length; i++) {
            course = new Course(nameList[i], imageList[i]);
            courseArrayList.add(course);
        }

        settingsButton = view.findViewById(R.id.profileUserPicture);
        documentReference = collectionReference.document(userID);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            userModel = documentSnapshot.toObject(UserModel.class);
            if (userModel != null) {
                userPhoto = userModel.getProfilePhoto();
                Glide.with(getActivity()).load(userPhoto).into(settingsButton);
            }
        });

        listAdapter = new CourseListAdapter(courseArrayList);
        recyclerView = view.findViewById(R.id.courseListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener((view1, position) -> {
            HomeFragmentDirections.ActionHomeFragmentToFirstCourseFragment2 action =
                    HomeFragmentDirections.actionHomeFragmentToFirstCourseFragment2();
            Course selectedItem = courseArrayList.get(position);
            navController = Navigation.findNavController(view1);
            if (selectedItem.getName().equals("1 курс 1 семестр")) {
                action.setCourse(1);
                action.setSemester(1);
            } else if (selectedItem.getName().equals("1 курс 2 семестр")) {
                action.setCourse(1);
                action.setSemester(2);
            } else if (selectedItem.getName().equals("2 курс 1 семестр")) {
                action.setCourse(2);
                action.setSemester(1);
            } else if (selectedItem.getName().equals("2 курс 2 семестр")) {
                action.setCourse(2);
                action.setSemester(2);
            } else if (selectedItem.getName().equals("3 курс 1 семестр")) {
                action.setCourse(3);
                action.setSemester(1);
            } else if (selectedItem.getName().equals("3 курс 2 семестр")) {
                action.setCourse(3);
                action.setSemester(2);
            } else if (selectedItem.getName().equals("4 курс 1 семестр")) {
                action.setCourse(4);
                action.setSemester(1);
            } else if (selectedItem.getName().equals("4 курс 2 семестр")) {
                action.setCourse(4);
                action.setSemester(2);
            }
            courseArrayList.clear();
            navController.navigate(action);
        });


        settingsButton.setOnClickListener(view12 -> {
            navController = Navigation.findNavController(view12);
            navController.navigate(R.id.action_homeFragment_to_settingsFragment);
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}