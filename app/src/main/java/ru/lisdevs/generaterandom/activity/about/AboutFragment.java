package ru.lisdevs.generaterandom.activity.about;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.lisdevs.generaterandom.R;


public class AboutFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        setOnClickListener(root.findViewById(R.id.tvTg), "https://t.me/railcinec");
        setOnClickListener(root.findViewById(R.id.vk), "https://vk.com/club223400185");
        setOnClickListener(root.findViewById(R.id.gp), "https://play.google.com/store/apps/details?id=ru.lisdevs.generaterandom");
        setOnClickListener(root.findViewById(R.id.tvRu), "https://www.rustore.ru/catalog/app/ru.lisdevs.generaterandom");
        setOnClickListener(root.findViewById(R.id.site), "https://www.rustore.ru/catalog/app/ru.lisdevs.generaterandom");

        return root;
    }

    private void setOnClickListener(LinearLayout layout, String url) {
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }

}