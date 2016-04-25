package com.abdellah.pcsalon.myapplication.ListesSSP;

import android.widget.BaseExpandableListAdapter;
import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdellah.pcsalon.myapplication.R;

/**
 * Created by Younes on 16/03/2016.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<Groupe> groups;
    public LayoutInflater inflater;
    public Activity activity;
    private boolean longClick=false;

    public MyExpandableListAdapter(Activity act, SparseArray<Groupe> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).sites.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);

        TextView text = null;
        if (convertView == null) {
            //convertView = inflater.inflate(R.layout.test3, null);
        }
       // text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children);
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!longClick) {
                    System.out.println("hhhhhhhhhhhhhhhhhhh");
                    Toast.makeText(activity, children,
                            Toast.LENGTH_SHORT).show();
                }
                longClick = false;
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(activity, children,
                        Toast.LENGTH_SHORT).show();
                longClick = true;
               // ClassAjout.setLongclick(true);
                return false;
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).sites.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
          //  convertView = inflater.inflate(R.layout.test2, null);
        }
        Groupe group = (Groupe) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
