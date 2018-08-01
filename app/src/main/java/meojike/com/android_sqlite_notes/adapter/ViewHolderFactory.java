package meojike.com.android_sqlite_notes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import meojike.com.android_sqlite_notes.R;

public class ViewHolderFactory {
    RecyclerView.ViewHolder createViewHolder(ViewGroup parentViewGroup, LayoutInflater inflater) {
        return new NoteAdapter.NoteHolder(inflater.inflate(R.layout.item_recyclerview_note, parentViewGroup, false));
    }
}
