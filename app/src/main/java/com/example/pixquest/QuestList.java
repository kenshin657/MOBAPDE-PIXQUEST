package com.example.pixquest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QuestList extends ArrayAdapter<Quest> {

    private Activity context;
    ArrayList<Quest> quests;

    public QuestList(Activity context, ArrayList<Quest> quests){
        super(context, R.layout.layout_quest_list, quests);
        this.context = context;
        this.quests = quests;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_quest_list, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewReward = listViewItem.findViewById(R.id.textViewReward);
        TextView textViewDescription = listViewItem.findViewById(R.id.textViewDescription);

        Quest quest = quests.get(position);
        textViewName.setText(quest.getTitle());
        textViewReward.setText(Integer.toString(quest.getReward()));
        textViewDescription.setText(quest.getDescription());

        return listViewItem;
    }
}
