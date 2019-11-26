
package br.ifsc.edu.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edLogin, edSenha;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Pessoa p = new Pessoa("Cleber", "123.456.789-09", "m");
        databaseReference.child("pessoas").push().setValue(p);

        databaseReference.child("pessoas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pessoa p = dataSnapshot.getValue(Pessoa.class);
                Log.d("DatabasePessoas", p.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.toString());
            }
        });

        edLogin=findViewById(R.id.edLogin);
        edSenha=findViewById(R.id.edSenha);
    }

    public void autenticar(View view) {
        mAuth.signInWithEmailAndPassword(
                edLogin.getText().toString(), edSenha.getText().toString()
        ).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Log in realizado com sucesso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplication(), PrincipalActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Falha ao realizar o log in", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cadastrar(View view){
        mAuth.createUserWithEmailAndPassword(
                edLogin.getText().toString(),
                edSenha.getText().toString()
                ).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Cadastro concluído com sucesso", Toast.LENGTH_LONG).show();
                }
                else
                    {
                    Toast.makeText(getApplicationContext(), "Falha ao realizar o cadastro", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
