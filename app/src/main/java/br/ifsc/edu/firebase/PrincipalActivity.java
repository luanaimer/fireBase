package br.ifsc.edu.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrincipalActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        if (mUser != null){
            Log.d("FireBaseAuth", mUser.getEmail());
        }
        else{
            Log.d("FireBaseAuth", "Usuário não autenticado");
        }
    }

    public void logOut(View view){
        mAuth.signOut();
        finish();
    }
}
