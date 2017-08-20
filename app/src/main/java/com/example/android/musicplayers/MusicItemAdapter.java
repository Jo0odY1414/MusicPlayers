package com.example.android.musicplayers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link MusicItemAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link MusicItem} objects.
 */
public class MusicItemAdapter extends ArrayAdapter<MusicItem> {

    private int flag = 0;

    /**
     * Create a new {@link MusicItemAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param musicItems is the list of {@link MusicItem}s to be displayed.
     */
    public MusicItemAdapter(Context context, ArrayList<MusicItem> musicItems, int flag) {
        super(context, 0, musicItems);
        this.flag = flag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.music_item, parent, false);
        }

        // Get the {@link MusicItem} object located at this position in the list
        MusicItem currentMusicItem = getItem(position);


        TextView audioName = (TextView) listItemView.findViewById(R.id.textView_AudioName);
        audioName.setText(currentMusicItem.getNameSong());

        TextView audioArtist = (TextView) listItemView.findViewById(R.id.textView_AudioArtist);
        audioArtist.setText(currentMusicItem.getArtistsName());

        ImageView iconPlay = (ImageView) listItemView.findViewById(R.id.ic_play);

        Button buySongFromStore = (Button) listItemView.findViewById(R.id.button_buy_from_store);
        currentMusicItem.setButtonResource(buySongFromStore);
        currentMusicItem.setmButtonResourceId(R.id.button_buy_from_store);
        currentMusicItem.setmButtonResource(listItemView.findViewById(R.id.button_buy_from_store));

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.Image_music);

        if (currentMusicItem.hasImage())
            imageView.setImageResource(currentMusicItem.getmImageResourceId());

        if (flag == 1) {
            buySongFromStore.setVisibility(View.GONE);
            iconPlay.setVisibility(View.VISIBLE);
        } else if (flag == 2) {
            audioName.setText(currentMusicItem.getArtistsName());
            audioArtist.setText(currentMusicItem.getAlbumName());
            buySongFromStore.setText(currentMusicItem.getPrice());
            buySongFromStore.setVisibility(View.VISIBLE);
            iconPlay.setVisibility(View.GONE);
        } else if (flag == 3) {
            audioName.setText(currentMusicItem.getAlbumName());
            buySongFromStore.setText(currentMusicItem.getPrice());
            buySongFromStore.setVisibility(View.VISIBLE);
            iconPlay.setVisibility(View.GONE);
        }

        return listItemView;
    }
}