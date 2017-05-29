package eu.execom.chatapplicationspring2017.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;

import eu.execom.chatapplicationspring2017.R;
import eu.execom.chatapplicationspring2017.dao.UserDao;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, FirebaseAuth.AuthStateListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int GOOGLE_LOGIN_REQUEST_CODE = 1;

    private GoogleApiClient googleApiClient;

    private FirebaseAuth auth;


    /**
     * Method called when Login activity starts. Prepares data for google sign in.
     */
    @AfterViews
    void init() {

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        auth = FirebaseAuth.getInstance();
        //auth.addAuthStateListener(this);
    }

    @Click
    void signIn() {
        // clear previously used account for login so we can choose an account each time
        googleApiClient.clearDefaultAccountAndReconnect();

        final Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, GOOGLE_LOGIN_REQUEST_CODE);  // start google sign in
    }

    /**
     * After google sign in has finished.
     */
    @OnActivityResult(GOOGLE_LOGIN_REQUEST_CODE)
    public void onGoogleLogin(Intent data, int resultCode) {
        if (resultCode == RESULT_OK) {
            final GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        // if user has successfully logged in using google
        if (result.isSuccess()) {
            final GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        final AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);

        // we pass credentials to firebase
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Listener for authentication state.
     */
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        // if user has logged in we finish this activity and go back to home activity
        if (user != null) {
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            final Intent intent=new Intent();
            setResult(RESULT_OK,intent);

            finish();
        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(this);
    }

    @Override
    public void onBackPressed() {
        // Prevent going back to HomeActivity
        // TODO: Exit app
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
