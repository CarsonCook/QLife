package engsoc.qlife.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import engsoc.qlife.R;
import engsoc.qlife.Util;
import engsoc.qlife.database.local.DatabaseRow;
import engsoc.qlife.database.local.buildings.Building;
import engsoc.qlife.database.local.buildings.BuildingManager;
import engsoc.qlife.database.local.food.Food;
import engsoc.qlife.database.local.food.FoodManager;
import engsoc.qlife.interfaces.IQLActionbarFragment;
import engsoc.qlife.interfaces.IQLDrawerItem;
import engsoc.qlife.interfaces.IQLListFragmentWithChild;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 26/06/2017.
 * Fragment that displays the buildings in the phone/cloud database. When a building is clicked, it starts
 * OneBuildingFragment that provides details about the building.
 */
public class BuildingsFragment extends ListFragment implements IQLActionbarFragment, IQLDrawerItem, IQLListFragmentWithChild {

    public static final String TAG_FOOD_NAMES = "FOOD_NAMES";

    private BuildingManager mBuildingManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        setActionbarTitle();
        inflateListView();
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        onListItemChosen(v);
    }

    @Override
    public void onResume() {
        super.onResume();
        selectDrawer();
    }

    @Override
    public void onPause() {
        super.onPause();
        deselectDrawer();
    }

    @Override
    public void setActionbarTitle() {
        Util.setActionbarTitle(getString(R.string.fragment_buildings), (AppCompatActivity) getActivity());
    }

    @Override
    public void deselectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_buildings, false);
    }

    @Override
    public void selectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_buildings, true);
    }

    @Override
    public void onListItemChosen(View view) {
        Bundle args = setDataForOneItem(view);

        OneBuildingFragment oneBuildingFragment = new OneBuildingFragment();
        oneBuildingFragment.setArguments(args);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.content_frame, oneBuildingFragment).commit();
    }

    @Override
    public Bundle setDataForOneItem(View view) {
        Bundle args = new Bundle();
        FoodManager foodManager = new FoodManager(getActivity().getApplicationContext());
        String sId = ((TextView) view.findViewById(R.id.db_id)).getText().toString();
        Building building = mBuildingManager.getRow(Integer.parseInt(sId));
        ArrayList<Food> food = foodManager.getFoodForBuilding(Integer.parseInt(sId));

        ArrayList<String> foodNames = new ArrayList<>();
        for (Food oneFood : food) {
            foodNames.add(oneFood.getName());
        }

        //deal with special case building names - common short forms for long names
        switch (building.getName()) {
            case "Athletics and Recreation Centre (ARC)":
                args.putString(Building.COLUMN_NAME, "ARC");
                break;
            case "John Deutsch Centre (JDUC)":
                args.putString(Building.COLUMN_NAME, "JDUC");
                break;
            default:
                args.putString(Building.COLUMN_NAME, building.getName());
                break;
        }

        args.putString(Building.COLUMN_PURPOSE, building.getPurpose());
        args.putBoolean(Building.COLUMN_BOOK_ROOMS, building.getBookRooms());
        args.putBoolean(Building.COLUMN_ATM, building.getAtm());
        args.putDouble(Building.COLUMN_LAT, building.getLat());
        args.putDouble(Building.COLUMN_LON, building.getLon());
        args.putStringArrayList(TAG_FOOD_NAMES, foodNames);
        return args;
    }

    @Override
    public void inflateListView() {
        mBuildingManager = new BuildingManager(getActivity().getApplicationContext());
        ArrayList<HashMap<String, String>> buildingsList = new ArrayList<>();

        ArrayList<DatabaseRow> buildings = mBuildingManager.getTable();
        for (DatabaseRow row : buildings) {
            Building building = (Building) row;
            HashMap<String, String> map = new HashMap<>();
            map.put(Building.COLUMN_NAME, building.getName());
            map.put(Building.COLUMN_PURPOSE, building.getPurpose());
            String food = building.getFood() ? "Yes" : "No";
            map.put(Building.COLUMN_FOOD, food);
            map.put(FoodFragment.TAG_DB_ID, String.valueOf(building.getId()));
            buildingsList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), buildingsList,
                R.layout.buildings_list_item, new String[]{Building.COLUMN_NAME, Building.COLUMN_PURPOSE, Building.COLUMN_FOOD, FoodFragment.TAG_DB_ID},
                new int[]{R.id.name, R.id.purpose, R.id.food, R.id.db_id});
        setListAdapter(adapter);
    }
}
