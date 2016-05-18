package ua.com.elius.familycart;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.PersonBuffer;

import ua.com.elius.familycart.data.person.PersonColumns;
import ua.com.elius.familycart.data.person.PersonCursor;
import ua.com.elius.familycart.family.FamilyAdapter;
import ua.com.elius.familycart.family.SavePersonAsyncTask;


public class FamilyFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, ResultCallback<People.LoadPeopleResult>,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "FamilyFragment";

    private GoogleApiClient mGoogleApiClient;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FamilyAdapter mAdapter;

    private static final int FAMILY_LOADER = 0;

    public FamilyFragment() {
        // Required empty public constructor
    }

    public static FamilyFragment newInstance() {
        return new FamilyFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAMILY_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = SignActivity.getGoogleApiClient(getActivity(), this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_family, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.family_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FamilyAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                .setResultCallback(this);

//        List<String> userIds = new ArrayList<String>();
//        userIds.add("+ЕвгенийГузик");
//        Plus.PeopleApi.load(mGoogleApiClient, userIds).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
        if (loadPeopleResult.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();
            new SavePersonAsyncTask(getContext()).execute(personBuffer);
        } else {
            Log.e(TAG, "Error requesting visible circles: " + loadPeopleResult.getStatus());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(getActivity());
        }
    }

    private String[][] getDummyDataset() {
        return new String[][]{
                {"110059205292597600792", "Roman Nikolaenko", "https://lh5.googleusercontent.com/-eZqlZB4rLCI/AAAAAAAAAAI/AAAAAAAAAAA/qwI7WA9z87g/photo.jpg?sz=50"},
                {"107559506607410177669", "Андрей Мацак", "https://lh3.googleusercontent.com/-LeBBLd4guQo/AAAAAAAAAAI/AAAAAAAAAAA/2hvHWMFP4Kk/photo.jpg?sz=50"},
                {"113268660818802633071", "Виктор Гузик", "https://lh3.googleusercontent.com/-XhUKbeBWV6U/AAAAAAAAAAI/AAAAAAAAAAA/KiBU7UqoMtM/photo.jpg?sz=50"},
                {"118425590566821293387", "Владислав Шаповалов", "https://lh3.googleusercontent.com/-D8GV6sENqVc/AAAAAAAAAAI/AAAAAAAAAAA/PjVPKXUUgNM/photo.jpg?sz=50"},
                {"114567123818259557163", "Дмитрий Сорока", "https://lh3.googleusercontent.com/-CSoUcZAkMNo/AAAAAAAAAAI/AAAAAAAAAAA/R8_LojQC4sM/photo.jpg?sz=50"},
                {"116815977898830889299", "Дмитро Коваленко", "https://lh5.googleusercontent.com/-T6dWrZcLL-4/AAAAAAAAAAI/AAAAAAAARCg/uuLJvQWJ_g0/photo.jpg?sz=50"},
                {"113016350672643553191", "Евгений Гузик", "https://lh4.googleusercontent.com/-rnP0cJIts7o/AAAAAAAAAAI/AAAAAAAAAeM/IGd_cmvz5AM/photo.jpg?sz=50"},
                {"105897970702268785128", "Игорь Гузик", "https://lh3.googleusercontent.com/-lr-6UzEOYAs/AAAAAAAAAAI/AAAAAAAAAAA/yNve1L8jAMA/photo.jpg?sz=50"},
                {"110824960864022189816", "Максим Олефиренко", "https://lh4.googleusercontent.com/-CHs5x_BtGZg/AAAAAAAAAAI/AAAAAAAABPg/_Y7lUW9wX2E/photo.jpg?sz=50"},
                {"107898529553319853567", "Максим Олефиренко", "https://lh3.googleusercontent.com/-zRst9ZNDenY/AAAAAAAAAAI/AAAAAAAAACA/edbaPkHlAhg/photo.jpg?sz=50"},
                {"115157884406514363546", "Михаил Костюк", "https://lh5.googleusercontent.com/-2NrzSHw4ISw/AAAAAAAAAAI/AAAAAAAAACA/t6EPFodHiH4/photo.jpg?sz=50"},
                {"110701070445864906628", "Петро Мацькович", "https://lh5.googleusercontent.com/-CcPCvxcZ_9s/AAAAAAAAAAI/AAAAAAAAAHI/dR08wjhTEKc/photo.jpg?sz=50"},
                {"101228686986622079217", "Таня Гузик", "https://lh3.googleusercontent.com/-VoAQG3L07JE/AAAAAAAAAAI/AAAAAAAAAAA/qWpYrLSVHno/photo.jpg?sz=50"},
                {"112057735653162877298", "Татьяна Мукомел", "https://lh3.googleusercontent.com/-trd6r3FrO7U/AAAAAAAAAAI/AAAAAAAAAAA/MqWlyNo1ab8/photo.jpg?sz=50"},
                {"101721776426479645059", "Ярослав Станиславович Иванов", "https://lh3.googleusercontent.com/-5tRC66wKN2c/AAAAAAAAAAI/AAAAAAAAAAA/_-XA_PAhi9Y/photo.jpg?sz=50"},
        };
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                PersonColumns.CONTENT_URI,
                PersonColumns.ALL_COLUMNS,
                null,
                null,
                PersonColumns.DISPLAY_NAME);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(new PersonCursor(data));
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
