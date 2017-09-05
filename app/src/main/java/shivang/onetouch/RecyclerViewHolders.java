package shivang.onetouch;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView Image;
    public TextView Title;
    public TextView Content;
    public Button button;
    public TextView Author;
    //public RelativeLayout Background;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        Log.v("LL Resp : ", "RecyclerViewHolders");
        Image=(ImageView) itemView.findViewById(R.id.webview);
        //Background=(RelativeLayout) itemView.findViewById(R.id.imgLayout);
        Title = (TextView)itemView.findViewById(R.id.title);
        Content=(TextView) itemView.findViewById(R.id.content);
        button=(Button) itemView.findViewById(R.id.button);
        Author=(TextView) itemView.findViewById(R.id.author);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}