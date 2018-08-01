package meojike.com.android_sqlite_notes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import meojike.com.android_sqlite_notes.R;

public class NoteAdapter extends RecyclerView.Adapter {
    private List<String> notesNames;
    private ViewHolderFactory viewHolderFactory = new ViewHolderFactory();
    private ViewHolderBinder viewHolderBinder;

    public NoteAdapter(List<String> notesNames, ViewHolderBinder.MyBinderCallback myBinderCallback) {
        this.notesNames = notesNames;
        this.viewHolderBinder = new ViewHolderBinder(myBinderCallback);
//        this.viewHolderBinder = new ViewHolderBinder(myBinderCallback, textColor, textSize);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewHolderFactory.createViewHolder(parent, LayoutInflater.from(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolderBinder.bindViewHolder(holder, notesNames.get(position));
    }

    @Override
    public int getItemCount() {
        return notesNames.size();
    }


    public static class NoteHolder extends RecyclerView.ViewHolder  { //implements OnCLickListener
        public TextView noteName;
        public ImageButton deleteNote;

        NoteHolder(View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            deleteNote = itemView.findViewById(R.id.deleteNote);
        }
    }
}
