package com.minami.contacts.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.minami.contacts.MainActivity;
import com.minami.contacts.R;
import com.minami.contacts.UpdateActivity;
import com.minami.contacts.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    // 3. 멤버 변수와 상속자를 만든다
    Context context;
    ArrayList<Contact> contactArrayList;

    public ContactAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactArrayList.get(position);

        holder.txtName.setText(contact.name);
        holder.txtPhone.setText(contact.phone);
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    // 1. 메모리에 있는 데이터를 연결한 viewHolder를 만든다
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 새로운 updateactivity 실행

                    Intent intent = new Intent(context, UpdateActivity.class);

                    // 1.index
                    // 2. 이름
                    // 3. 전번
                    int index = getAdapterPosition();
                    Contact contact = contactArrayList.get(index);

                    intent.putExtra("index",index);
                    intent.putExtra("name", contact.name);
                    intent.putExtra("phone", contact.phone);



                    ((MainActivity)context).launcher.launch(intent);

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showAlertDialog();

                }
            });


        }

        private void showAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("주소록 삭제");
            builder.setMessage("정말삭제하시겠습니까?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 몇번째 데이터를 선택했는지 알아야 한다
                    int index = getAdapterPosition();

                    // 어레이리스트에서 삭제하고
                    contactArrayList.remove(index);

                    // 화면에 보여준다
                    notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("NO", null);
            builder.show();


        }
    }
}
