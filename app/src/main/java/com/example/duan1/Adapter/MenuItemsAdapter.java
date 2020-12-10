package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duan1.Model.ProductDrink;
import com.example.duan1.R;

import java.util.List;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.MenuItemsViewHolder> {

    private List<ProductDrink> menuItemsList;
    Context context;

    public MenuItemsAdapter(List<ProductDrink> menuItemsList, Context context) {
        this.menuItemsList = menuItemsList;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(@NonNull MenuItemsViewHolder holder, int position) {
        ProductDrink menuItems = menuItemsList.get(position);
        if(menuItemsList != null){
            holder.txtallitems.setText(menuItems.getNameDrink());
        }
    }

    @NonNull
    @Override
    public MenuItemsAdapter.MenuItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allmenu_recycler_items,parent,false);
        return new MenuItemsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (menuItemsList != null){
            return menuItemsList.size();
        }else{
            return 0;
        }
    }

    public class MenuItemsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgallitems;
        private TextView txtallitems;

        public MenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgallitems = itemView.findViewById(R.id.all_menu_image);
            txtallitems = itemView.findViewById(R.id.all_menu_name);
        }
    }
}



