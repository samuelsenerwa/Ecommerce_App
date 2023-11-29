package com.example.individualproject.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.individualproject.R;
import com.example.individualproject.activities.ShowAllActivity;
import com.example.individualproject.adapters.CategoryAdapter;
import com.example.individualproject.adapters.NewProductsAdapter;
import com.example.individualproject.adapters.PopularProductsAdapter;
import com.example.individualproject.models.CategoryModel;
import com.example.individualproject.models.NewProductsModel;
import com.example.individualproject.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView catShowAll, popularShowAll, newProductShowAll;

    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerView, newProductRecyclerView, popularRecyclerView;

//    category recyclerView
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

//    New Product RecyclerView
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelsList;

//    Popular Products
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelsList;

//    FireStore
    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(getActivity());
        catRecyclerView = root.findViewById(R.id.rec_category);
        newProductRecyclerView = root.findViewById(R.id.new_product_rec);
        popularRecyclerView = root.findViewById(R.id.popular_rec);
        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });


            linearLayout = root.findViewById(R.id.home_layout);
            linearLayout.setVisibility(View.GONE);
//       image Slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.shoe,"Discount on Shoes Items", ScaleTypes.CENTER_CROP ));
        slideModels.add(new SlideModel(R.drawable.electronic,"Discount on Electronics", ScaleTypes.CENTER_CROP ));
        slideModels.add(new SlideModel(R.drawable.oil,"70% OFF", ScaleTypes.CENTER_CROP ));

        imageSlider.setImageList((slideModels));

        progressDialog.setTitle("Welcome to Ecommerce App");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


//        Category
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList= new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);

        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if( task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        CategoryModel categoryModel = document.toObject(CategoryModel.class);
                        categoryModelList.add(categoryModel);
                        categoryAdapter.notifyDataSetChanged();
                        linearLayout.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        New Products
        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelsList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelsList);
        newProductRecyclerView.setAdapter(newProductsAdapter);

        db.collection("NewProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if( task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                        newProductsModelsList.add(newProductsModel);
                        newProductsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Popular Products
        popularRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelsList = new ArrayList<>();
        popularProductsAdapter= new PopularProductsAdapter(getContext(), popularProductsModelsList);
        popularRecyclerView.setAdapter(popularProductsAdapter);

        db.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if( task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                       PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                 popularProductsModelsList.add(popularProductsModel);
                        popularProductsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  root;

    }
}