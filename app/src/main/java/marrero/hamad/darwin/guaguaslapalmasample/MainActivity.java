package marrero.hamad.darwin.guaguaslapalmasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.popup.PopupDefinition;
import com.esri.arcgisruntime.mapping.view.MapView;


public class MainActivity extends AppCompatActivity {

    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        final ArcGISMap map = new ArcGISMap();
        map.setBasemap(Basemap.createStreets());
        map.setInitialViewpoint(createViewpoint());
        String serviceFeatureTableURL = getResources().getString(R.string.busStopsLayer);
        ServiceFeatureTable featureTable = new ServiceFeatureTable(serviceFeatureTableURL);
        featureTable.setFeatureRequestMode(ServiceFeatureTable.FeatureRequestMode.ON_INTERACTION_CACHE);
        FeatureLayer featureLayer = new FeatureLayer(featureTable);
        featureLayer.setMinScale(200000);
        PopupDefinition popupDefinition = new PopupDefinition(featureLayer);
        featureLayer.setPopupDefinition(popupDefinition);
        map.getOperationalLayers().add(featureLayer);
        mapView.setMap(map);
    }

    private Viewpoint createViewpoint() {
        double X_COORDINATE = 1987209.861358;
        double Y_COORDINATE = 3333189.492432;
        return new Viewpoint(new Point(-X_COORDINATE, Y_COORDINATE, getWebMercator()), 475000);
    }

    private SpatialReference getWebMercator() {
        return SpatialReferences.getWebMercator();
    }

    @Override
    protected void onPause(){
        mapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.resume();
    }
}

