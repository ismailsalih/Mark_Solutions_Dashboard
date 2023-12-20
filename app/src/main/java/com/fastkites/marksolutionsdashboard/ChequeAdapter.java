package com.fastkites.marksolutionsdashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChequeAdapter extends RecyclerView.Adapter<ChequeAdapter.viewHolder> {

    List<ChequeModel> chequeModelList;
    int position;

    public ChequeAdapter(List<ChequeModel> chequeModelList) {
        this.chequeModelList = chequeModelList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cheques_list_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int globalPosition) {
        position = holder.getAdapterPosition();
        holder.nameOnCheque.setText(chequeModelList.get(position).getNameOnCheque());
        holder.chequeNo.setText(chequeModelList.get(position).getNumber());
        holder.dueDate.setText(chequeModelList.get(position).getDueDate());
        holder.bank.setText(chequeModelList.get(position).getBank());
        holder.amount.setText("Amount: " + chequeModelList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return chequeModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView nameOnCheque, chequeNo, dueDate, bank, amount;
        ImageView bankLogo;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            nameOnCheque = itemView.findViewById(R.id.nameOnCheque);
            chequeNo = itemView.findViewById(R.id.chequeNo);
            dueDate = itemView.findViewById(R.id.dueDate);
            bank = itemView.findViewById(R.id.bank);
            amount = itemView.findViewById(R.id.amount);
            bankLogo = itemView.findViewById(R.id.bankLogo);

        }
    }
}
