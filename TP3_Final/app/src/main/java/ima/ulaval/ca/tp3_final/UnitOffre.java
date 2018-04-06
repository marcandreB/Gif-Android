package ima.ulaval.ca.tp3_final;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Meh on 2018-04-06.
 */

public class UnitOffre {
    private String marque;
    private String model;
    private String description;
    private String price;
    private String transmission;
    private String created;
    private String updated;
    private String seller;
    private String year;
    private String image;

    public UnitOffre(String Marque, String Model, String Description, String Price, String Transmission, String Created, String Updated, String Seller, String Year, String Image){
        this.marque = Marque;
        this.model = Model;
        this.description = Description;
        this.price = Price;
        this.transmission = Transmission;
        this.created = Created;
        this.updated = Updated;
        this.seller = Seller;
        this.year = Year;
        this.image = Image;
    }
}
