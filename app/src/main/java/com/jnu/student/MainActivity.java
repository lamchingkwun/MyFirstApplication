package com.jnu.student;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.data.BookItem;
import com.jnu.student.data.DataBank;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    private String []tabHeaderStrings = {"Shopping items","baidu maps","News"};

    private String []tabHeaderStrings  = {"图书","地图","新闻","时钟"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycleview);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
                // 设置TabLayout的标题
        ).attach();
    }

    private class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 4;

        public FragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);

        }


        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ShoppingListFragment();
                case 1:
                    return new TencentMapFragment();
                case 2:
                    return new WebViewFragment();
                case 3:
                    return new GameViewFragment();
                default:
                    return null;
            }
        }


        public int getItemCount() {
            return NUM_TABS;
        }
    }
//
//    private BookItemAdapter bookItemAdapter;
//
//    private ArrayList<BookItem> bookItems=new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        RecyclerView mainRecyclerview=findViewById(R.id.recycle_view_books);
//        mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));
//
//        bookItems = new DataBank().LoadBookItems(MainActivity.this);
//        if(0==bookItems.size()){
//            bookItems.add(new BookItem("软件项目管理案例教程（第4版）",R.drawable.book_2));
//            bookItems.add(new BookItem("创新工程实践",R.drawable.book_no_name));
//            bookItems.add(new BookItem("信息安全数学基础（第2版）",R.drawable.book_1));
//        }
//
//        bookItemAdapter =new BookItemAdapter(bookItems);
//        mainRecyclerview.setAdapter(bookItemAdapter);
//
//        registerForContextMenu(mainRecyclerview);
//
//        addItemLauncher= registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//
//                        String name = data.getStringExtra("name");
//                        bookItems.add(new BookItem(name,R.drawable.book_no_name));
//                        bookItemAdapter.notifyItemInserted(bookItems.size());
//
//
//                        new DataBank().SaveBookItems(MainActivity.this,bookItems);
//
//                        //获取返回的数据//在这塑可以根据需要进行进一步处理
//                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
//
//                    }
//                }
//        );
//        updateItemLauncher= registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        int position = data.getIntExtra("position",0);
//                        String name = data.getStringExtra("name");
//                        BookItem bookItem = bookItems.get(position);
//                        bookItem.setName(name);
//                        bookItemAdapter.notifyItemChanged(position);
//
//                        new DataBank().SaveBookItems(MainActivity.this,bookItems);
//
//                        //获取返回的数据//在这塑可以根据需要进行进一步处理
//                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
//
//                    }
//                }
//        );
//
//
//    }
//    ActivityResultLauncher<Intent> addItemLauncher;
//    ActivityResultLauncher<Intent> updateItemLauncher;
//    private static final int MENU_ITEM_ADD = 0;
//    private static final int MENU_ITEM_DELETE = 1;
//    private static final int MENU_ITEM_UPDATE = 2;
//    public boolean onContextItemSelected(MenuItem item) {
//        int position = item.getOrder();  // 获取被点击的项的位置
//        switch (item.getItemId()) {
//            case MENU_ITEM_ADD:
//
//                Intent intent = new Intent(MainActivity.this,BookItemDetailsActivity.class);
//                addItemLauncher.launch(intent);
//                break;
//
//            case MENU_ITEM_DELETE:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Delete Data");
//                builder.setMessage("Are you sure you want to delete this data?");
//                builder.setPositiveButton( "确定",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int which) {
//                        bookItems.remove(item.getOrder());
//                        bookItemAdapter.notifyItemRemoved(item.getOrder());
//
//
//                        new DataBank().SaveBookItems(MainActivity.this,bookItems);
//                    }
//
//                });
//                builder.setNegativeButton( "取消",new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {}
//                });
//                builder.create().show();
//                break;
//            case MENU_ITEM_UPDATE:
//                Intent intentUpdate = new Intent(MainActivity.this,BookItemDetailsActivity.class);
//                BookItem bookItem = bookItems.get(item.getOrder());
//                intentUpdate.putExtra("name",bookItem.getName());
//                intentUpdate.putExtra("position",item.getOrder());
//                updateItemLauncher.launch(intentUpdate);
//                break;
//            default:
//                return super.onContextItemSelected(item);
//        }
//        return true;
//    }
//
//    public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {
//
//        private ArrayList<BookItem> bookItemArrayList;
//
//        /**
//         * Provide a reference to the type of views that you are using
//         * (custom ViewHolder)
//         */
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
//            private final TextView textViewName;
//
//            private final ImageView ImageViewNameItem;
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.setHeaderTitle("具体操作");
//                menu.add( 0,0, this.getAdapterPosition(),"添加"+this.getAdapterPosition());
//                menu.add(0,1, this.getAdapterPosition(),"删除"+this.getAdapterPosition());
//                menu.add(0,2,this.getAdapterPosition(),"修改"+this.getAdapterPosition());
//            }
//            public ViewHolder(View bookitemView) {
//                super(bookitemView);
//                // Define click listener for the ViewHolder's View
//
//                textViewName = bookitemView.findViewById(R.id.text_view_book_title);
//                ImageViewNameItem = bookitemView.findViewById(R.id.bookView_item1);
//                bookitemView.setOnCreateContextMenuListener(this);
//            }
//
//            public TextView getTextViewName() {
//                return textViewName;
//            }
//            public ImageView getImageViewNameItem() {
//                return ImageViewNameItem;
//            }
//
//
//        }
//
//
//        /**
//         * Initialize the dataset of the Adapter
//         *
//         * @param dataSet String[] containing the data to populate views to be used
//         * by RecyclerView
//         */
//        public BookItemAdapter(ArrayList<BookItem> dataSet) {
//            bookItemArrayList = dataSet;
//        }
//
//        // Create new views (invoked by the layout manager)
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            // Create a new view, which defines the UI of the list item
//            View view = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.book_item_row, viewGroup, false);
//
//            return new ViewHolder(view);
//        }
//
//
//
//        // Replace the contents of a view (invoked by the layout manager)
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
//
//            // Get element from your dataset at this position and replace the
//            // contents of the view with that element
//            viewHolder.getTextViewName().setText(bookItemArrayList.get(position).getName());
//            viewHolder.getImageViewNameItem().setImageResource(bookItemArrayList.get(position).getImageId());
//        }
//
//        // Return the size of your dataset (invoked by the layout manager)
//        @Override
//        public int getItemCount() {
//            return bookItemArrayList.size();
//        }
//    }

}
